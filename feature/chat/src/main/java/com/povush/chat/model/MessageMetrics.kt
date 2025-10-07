package com.povush.chat.model

data class MessageMetrics(
    val model: String,
    val responseTimeMs: Long,
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int,
    val cost: Double
) {
    fun formatCost(): String = if (cost == 0.0) "Бесплатно" else "$${String.format("%.6f", cost)}"
    fun formatTime(): String = "${responseTimeMs}ms"
    fun formatTokens(): String = "$totalTokens токенов (prompt: $promptTokens, completion: $completionTokens)"
}

