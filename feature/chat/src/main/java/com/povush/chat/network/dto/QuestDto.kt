package com.povush.chat.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestDto(
    val title: String,
    val description: String,
    val tasks: List<String> = emptyList(),
)
