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
    data class QuestGenerationResult(
        val quest: QuestDto,
        val json: String
    )

    private val basicSystemPrompt = listOf(ChatMessageDto("system", ChatConfig.questGeneratorSystemPrompt))

    suspend fun createQuest(description: String): QuestGenerationResult {
        val descriptionSystemPrompt = listOf(ChatMessageDto("system", description))
        val finalPrompt = basicSystemPrompt + descriptionSystemPrompt
        val request = ChatRequestDto(messages = finalPrompt)

        val response = openRouterService.chatCompletion(request)
        val responseContent = response.choices.firstOrNull()?.message?.content ?: throw NullPointerException("Ответ от ИИ не должен быть null!")
        val quest = runCatching { questAdapter.fromJson(responseContent) }.getOrNull() ?: throw NullPointerException("Квест не должен быть null!")
        val formattedJson = questAdapter.indent("  ").toJson(quest)

        return QuestGenerationResult(
            quest = quest,
            json = formattedJson
        )
    }
}
