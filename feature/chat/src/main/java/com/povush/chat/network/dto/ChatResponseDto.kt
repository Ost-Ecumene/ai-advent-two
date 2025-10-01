package com.povush.chat.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatResponseDto(
    val choices: List<ChatChoiceDto> = emptyList(),
)