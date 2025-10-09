package com.povush.chat.service

import com.povush.chat.model.Agent
import com.povush.chat.model.AgentTask
import com.povush.chat.model.MessageMetrics
import com.povush.chat.network.OpenRouterService
import com.povush.chat.network.dto.ChatMessageDto
import com.povush.chat.network.dto.ChatRequestDto
import com.povush.chat.ChatConfig
import javax.inject.Inject

/**
 * Сервис для управления взаимодействием агентов
 */
class AgentService @Inject constructor(
    private val openRouterService: OpenRouterService
) {
    
    /**
     * Выполнить задачу с помощью двух агентов:
     * 1. Агент-Писатель создаёт решение
     * 2. Агент-Ревьюер проверяет работу
     */
    suspend fun executeAgentTask(
        userTask: String,
        temperature: Double,
        model: String
    ): AgentTask {
        // Шаг 1: Агент-Писатель выполняет задачу
        val (writerResponse, writerMetrics) = executeAgentStep(
            agent = Agent.Writer,
            userMessage = userTask,
            temperature = temperature,
            model = model
        )
        
        // Шаг 2: Агент-Ревьюер проверяет работу
        val reviewPrompt = buildReviewPrompt(userTask, writerResponse)
        val (reviewerResponse, reviewerMetrics) = executeAgentStep(
            agent = Agent.Reviewer,
            userMessage = reviewPrompt,
            temperature = temperature,
            model = model
        )
        
        return AgentTask(
            userTask = userTask,
            writerResponse = writerResponse,
            reviewerResponse = reviewerResponse,
            writerMetrics = writerMetrics,
            reviewerMetrics = reviewerMetrics
        )
    }
    
    /**
     * Выполнить один шаг работы агента
     */
    private suspend fun executeAgentStep(
        agent: Agent,
        userMessage: String,
        temperature: Double,
        model: String
    ): Pair<String, MessageMetrics?> {
        val messages = listOf(
            ChatMessageDto("system", agent.systemPrompt),
            ChatMessageDto("user", userMessage)
        )
        
        val request = ChatRequestDto(
            messages = messages,
            model = model,
            temperature = temperature
        )
        
        val startTime = System.currentTimeMillis()
        val response = openRouterService.chatCompletion(request)
        val responseTime = System.currentTimeMillis() - startTime
        
        val responseContent = response.choices.firstOrNull()?.message?.content 
            ?: throw NullPointerException("Ответ от ${agent.name} не должен быть null!")
        
        val metrics = response.usage?.let { usage ->
            MessageMetrics(
                model = model,
                responseTimeMs = responseTime,
                promptTokens = usage.promptTokens,
                completionTokens = usage.completionTokens,
                totalTokens = usage.totalTokens,
                cost = ChatConfig.ModelPricing.getCost(model, usage.promptTokens, usage.completionTokens)
            )
        }
        
        return Pair(responseContent, metrics)
    }
    
    /**
     * Построить промпт для ревьюера
     */
    private fun buildReviewPrompt(userTask: String, writerResponse: String): String {
        return """
            ИСХОДНОЕ ЗАДАНИЕ:
            $userTask
            
            РАБОТА АГЕНТА-ПИСАТЕЛЯ:
            $writerResponse
            
            Проанализируй эту работу и дай развёрнутую обратную связь.
        """.trimIndent()
    }
}

