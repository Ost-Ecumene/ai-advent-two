package com.povush.chat.repository.impl

import com.povush.chat.ChatConfig
import com.povush.chat.model.ChatItem
import com.povush.chat.model.Role
import com.povush.chat.network.OpenRouterService
import com.povush.chat.network.dto.ChatMessageDto
import com.povush.chat.network.dto.ChatRequestDto
import com.povush.chat.repository.ChatRepository
import com.povush.chat.service.QuestGeneratorLLMService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class ChatRepositoryImpl @Inject constructor(
    private val openRouterService: OpenRouterService,
    private val questGeneratorLLMService: QuestGeneratorLLMService
) : ChatRepository() {
    private val chatSystemPrompt = listOf(ChatMessageDto("system", ChatConfig.companionSystemPrompt))
    private val basicHistory = listOf<ChatItem>(
//        ChatItem.Message(
//            text = ChatConfig.FIRST_MESSAGE,
//            role = Role.Assistant
//        )
    )

    private val _chatHistory = MutableStateFlow<List<ChatItem>>(basicHistory)
    override val chatHistory = _chatHistory.asStateFlow()

    override suspend fun sendRequest(message: String) {
        _chatHistory.update {
            it + ChatItem.Message(
                text = message,
                role = Role.User
            )
        }

        val messages = chatSystemPrompt + chatHistory.value.map { chatItem ->
            when (chatItem) {
                is ChatItem.Message -> ChatMessageDto(
                    role = chatItem.role.internalName,
                    content = chatItem.text
                )
                is ChatItem.Quest -> ChatMessageDto(
                    role = Role.User.internalName,
                    content = chatItem.questJson
                )
                is ChatItem.Log -> ChatMessageDto(
                    role = chatItem.role.internalName,
                    content = chatItem.text
                )
            }
        }

        val request = ChatRequestDto(messages = messages)
        val response = openRouterService.chatCompletion(request)
        val responseContent = response.choices.firstOrNull()?.message?.content ?: throw NullPointerException("Ответ от ИИ не должен быть null!")

        val answer = if (responseContent.startsWith("Инициировать создание квеста!")) {
            val description = responseContent.removePrefix("Инициировать создание квеста!")
            val log = ChatItem.Log(
                text = description,
                role = Role.Assistant
            )
            _chatHistory.update { it + log }
            val questResult = questGeneratorLLMService.createQuest(description)
            ChatItem.Quest(
                quest = questResult.quest,
                questJson = questResult.questJson
            )
        } else {
            ChatItem.Message(
                text = responseContent,
                role = Role.Assistant
            )
        }

        _chatHistory.update { it + answer }
    }
}
