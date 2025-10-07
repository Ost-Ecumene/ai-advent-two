package com.povush.chat.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatResponseDto(
    val choices: List<ChatChoiceDto> = emptyList(),
    val usage: UsageDto? = null,
    val model: String? = null
)

@JsonClass(generateAdapter = true)
data class UsageDto(
    @Json(name = "prompt_tokens") val promptTokens: Int = 0,
    @Json(name = "completion_tokens") val completionTokens: Int = 0,
    @Json(name = "total_tokens") val totalTokens: Int = 0
)