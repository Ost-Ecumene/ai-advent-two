package com.povush.chat.network.dto

import com.povush.chat.ChatConfig
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatRequestDto(
    val messages: List<ChatMessageDto>,
    val model: String = ChatConfig.CURRENT_MODEL,
    val temperature: Double? = ChatConfig.BASE_TEMPERATURE,
)