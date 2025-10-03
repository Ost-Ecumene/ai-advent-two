package com.povush.chat.network.dto

import com.povush.chat.ChatConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatRequestDto(
    val messages: List<ChatMessageDto>,
    val model: String = ChatConfig.CURRENT_MODEL,
    val temperature: Double? = ChatConfig.BASE_TEMPERATURE,
    @Json(name = "max_tokens")
    val maxTokens: Int? = ChatConfig.MAX_OUTPUT_TOKENS,
    @Json(name = "stop")
    val stopSequences: List<String>? = ChatConfig.DEFAULT_STOP_SEQUENCES,
)
