package com.povush.chat.util

/**
 * Утилита для обрезки сообщений пользователя с уведомлением
 */
object MessageTruncator {
    
    private const val MAX_TOKENS = 2000
    private const val TRUNCATION_NOTICE = "\n\n⚠️ *Сообщение было обрезано из-за превышения лимита в 2000 токенов*"
    
    /**
     * Обрезает сообщение пользователя если оно превышает лимит токенов
     * @param message исходное сообщение
     * @return пара: (обработанное сообщение, было ли обрезано)
     */
    fun processUserMessage(message: String): Pair<String, Boolean> {
        val (truncatedMessage, wasTruncated) = TokenCounter.truncateToTokens(message, MAX_TOKENS)
        
        return if (wasTruncated) {
            Pair(truncatedMessage + TRUNCATION_NOTICE, true)
        } else {
            Pair(message, false)
        }
    }
    
    /**
     * Проверяет, нужно ли обрезать сообщение
     * @param message сообщение для проверки
     * @return true если сообщение превышает лимит токенов
     */
    fun shouldTruncate(message: String): Boolean {
        return TokenCounter.countTokens(message) > MAX_TOKENS
    }
    
    /**
     * Получает количество токенов в сообщении
     * @param message сообщение
     * @return количество токенов
     */
    fun getTokenCount(message: String): Int {
        return TokenCounter.countTokens(message)
    }
}
