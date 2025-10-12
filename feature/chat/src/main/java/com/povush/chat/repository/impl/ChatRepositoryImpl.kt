package com.povush.chat.repository.impl

import com.povush.chat.ChatConfig
import com.povush.chat.model.ChatItem
import com.povush.chat.model.Role
import com.povush.chat.network.OpenRouterService
import com.povush.chat.network.dto.ChatMessageDto
import com.povush.chat.network.dto.ChatRequestDto
import com.povush.chat.network.dto.toText
import com.povush.chat.repository.ChatRepository
import com.povush.chat.service.QuestGeneratorLLMService
import com.povush.chat.util.MessageTruncator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class ChatRepositoryImpl @Inject constructor(
    private val openRouterService: OpenRouterService,
    private val questGeneratorLLMService: QuestGeneratorLLMService,
    private val agentService: com.povush.chat.service.AgentService
) : ChatRepository() {
    private val chatSystemPrompt = listOf(ChatMessageDto("system", ""))
    private val basicHistory = listOf(
        ChatItem.Message(
            text = ChatConfig.FIRST_MESSAGE,
            role = Role.Assistant
        )
    )

    private val _chatHistory = MutableStateFlow<List<ChatItem>>(basicHistory)
    override val chatHistory = _chatHistory.asStateFlow()

    override suspend fun sendRequest(message: String, temperature: Double, model: String) {
        // Обрабатываем сообщение пользователя - обрезаем если превышает лимит токенов
        val (processedMessage, wasTruncated) = MessageTruncator.processUserMessage(message)
        
        _chatHistory.update {
            it + ChatItem.Message(
                text = processedMessage,
                role = Role.User
            )
        }
        
        // Если сообщение было обрезано, показываем уведомление
        if (wasTruncated) {
            _chatHistory.update {
                it + ChatItem.Log(
                    text = "⚠️ Ваше сообщение было обрезано из-за превышения лимита в 2000 токенов. Токенов в исходном сообщении: ${MessageTruncator.getTokenCount(message)}",
                    role = Role.Assistant
                )
            }
        }
        
        // Проверяем, нужно ли запустить агентский workflow
        if (processedMessage.trim().startsWith("/агенты", ignoreCase = true) || 
            processedMessage.trim().startsWith("/agents", ignoreCase = true)) {
            // Извлекаем задачу для агентов
            val task = processedMessage.removePrefix("/агенты").removePrefix("/agents").trim()
            
            if (task.isEmpty()) {
                _chatHistory.update {
                    it + ChatItem.Message(
                        text = "Пожалуйста, укажите задачу для агентов. Например: /агенты напиши функцию для сортировки массива",
                        role = Role.Assistant
                    )
                }
                return
            }
            
            // Показываем статус
            _chatHistory.update {
                it + ChatItem.Log(
                    text = "🔄 Запуск агентского workflow...\n${com.povush.chat.model.Agent.Writer.emoji} ${com.povush.chat.model.Agent.Writer.name} начинает работу...",
                    role = Role.Assistant
                )
            }
            
            try {
                // Выполняем задачу через агентов
                val agentTask = agentService.executeAgentTask(task, temperature, model)
                
                // Добавляем результат в историю
                _chatHistory.update {
                    it + ChatItem.AgentInteraction(
                        task = agentTask,
                        status = com.povush.chat.model.AgentTaskStatus.COMPLETED
                    )
                }
            } catch (e: Exception) {
                _chatHistory.update {
                    it + ChatItem.Message(
                        text = "❌ Ошибка при выполнении агентского workflow: ${e.message}",
                        role = Role.Assistant
                    )
                }
            }
            return
        }

        val messages = chatSystemPrompt + chatHistory.value.map { chatItem ->
            when (chatItem) {
                is ChatItem.Message -> ChatMessageDto(
                    role = chatItem.role.internalName,
                    content = chatItem.text
                )
                is ChatItem.Quest -> ChatMessageDto(
                    role = Role.User.internalName,
                    content = chatItem.quest.toText()
                )
                is ChatItem.Log -> ChatMessageDto(
                    role = chatItem.role.internalName,
                    content = chatItem.text
                )
                is ChatItem.AgentInteraction -> ChatMessageDto(
                    role = Role.Assistant.internalName,
                    content = "Задача: ${chatItem.task.userTask}\n\nРезультат работы агентов выполнен."
                )
            }
        }

        val request = ChatRequestDto(messages = messages, model = model, temperature = temperature)
        
        // Замеряем время выполнения запроса
        val startTime = System.currentTimeMillis()
        val response = openRouterService.chatCompletion(request)
        val responseTime = System.currentTimeMillis() - startTime
        
        val responseContent = response.choices.firstOrNull()?.message?.content ?: throw NullPointerException("Ответ от ИИ не должен быть null!")
        
        // Рассчитываем метрики
        val usage = response.usage
        val metrics = if (usage != null) {
            com.povush.chat.model.MessageMetrics(
                model = model,
                responseTimeMs = responseTime,
                promptTokens = usage.promptTokens,
                completionTokens = usage.completionTokens,
                totalTokens = usage.totalTokens,
                cost = ChatConfig.ModelPricing.getCost(model, usage.promptTokens, usage.completionTokens)
            )
        } else {
            null
        }

        val answer = if (responseContent.startsWith("Инициировать создание квеста!")) {
            val description = responseContent.removePrefix("Инициировать создание квеста!")
            val log = ChatItem.Log(
                text = description,
                role = Role.Assistant
            )
            _chatHistory.update { it + log }
            val quest = questGeneratorLLMService.createQuest(description, temperature)
            ChatItem.Quest(quest = quest)
        } else {
            ChatItem.Message(
                text = responseContent,
                role = Role.Assistant,
                metrics = metrics
            )
        }

        _chatHistory.update { it + answer }
    }
}