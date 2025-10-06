package com.povush.chat.service

import com.povush.chat.ChatConfig
import com.povush.chat.network.dto.ChatMessageDto
import com.povush.chat.network.dto.ChatRequestDto
import com.povush.chat.network.dto.QuestDto
import com.povush.chat.network.OpenRouterService
import com.squareup.moshi.JsonAdapter
import javax.inject.Inject

class QuestGeneratorLLMService @Inject constructor(
    private val openRouterService: OpenRouterService,
    private val questAdapter: JsonAdapter<QuestDto>
) {
    private val basicSystemPrompt = listOf(ChatMessageDto("system", ""))

    suspend fun createQuest(description: String, temperature: Double): QuestDto {
        val descriptionSystemPrompt = listOf(ChatMessageDto("system", description))
        val finalPrompt = basicSystemPrompt + descriptionSystemPrompt
        val request = ChatRequestDto(messages = finalPrompt, temperature = temperature)

        val response = openRouterService.chatCompletion(request)
        val responseContent = response.choices.firstOrNull()?.message?.content ?: throw NullPointerException("Ответ от ИИ не должен быть null!")
        val quest = runCatching { questAdapter.fromJson(responseContent) }.getOrNull() ?: throw NullPointerException("Квест не должен быть null!")

        return quest
    }
}