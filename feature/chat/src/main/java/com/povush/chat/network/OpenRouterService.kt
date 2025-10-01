package com.povush.chat.network

import com.povush.chat.network.dto.ChatRequestDto
import com.povush.chat.network.dto.ChatResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenRouterService {
    @POST("chat/completions")
    suspend fun chatCompletion(
        @Body body: ChatRequestDto,
    ): ChatResponseDto
}