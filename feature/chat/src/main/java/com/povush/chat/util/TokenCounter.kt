package com.povush.chat.util

/**
 * Утилита для подсчета токенов в тексте
 * Использует приблизительный алгоритм подсчета токенов для GPT моделей
 */
object TokenCounter {
    
    /**
     * Подсчитывает приблизительное количество токенов в тексте
     * Алгоритм основан на том, что в среднем 1 токен ≈ 4 символа для английского текста
     * и ≈ 2-3 символа для русского текста
     */
    fun countTokens(text: String): Int {
        if (text.isEmpty()) return 0
        
        // Подсчитываем количество слов и символов
        val words = text.split("\\s+".toRegex()).filter { it.isNotEmpty() }
        val characters = text.length
        
        // Приблизительный расчет:
        // - Для английского: ~4 символа на токен
        // - Для русского: ~2.5 символа на токен
        // - Смешанный текст: усредняем
        
        val russianChars = text.count { it in 'а'..'я' || it in 'А'..'Я' || it in 'ё'..'ё' || it in 'Ё'..'Ё' }
        val englishChars = text.count { it in 'a'..'z' || it in 'A'..'Z' }
        val otherChars = characters - russianChars - englishChars
        
        // Рассчитываем токены для разных типов символов
        val russianTokens = (russianChars / 2.5).toInt()
        val englishTokens = (englishChars / 4.0).toInt()
        val otherTokens = (otherChars / 3.0).toInt()
        
        return (russianTokens + englishTokens + otherTokens).coerceAtLeast(1)
    }
    
    /**
     * Обрезает текст до указанного количества токенов
     * @param text исходный текст
     * @param maxTokens максимальное количество токенов
     * @return пара: (обрезанный текст, был ли текст обрезан)
     */
    fun truncateToTokens(text: String, maxTokens: Int): Pair<String, Boolean> {
        if (countTokens(text) <= maxTokens) {
            return Pair(text, false)
        }
        
        // Бинарный поиск для нахождения оптимальной длины
        var left = 0
        var right = text.length
        var bestLength = 0
        
        while (left <= right) {
            val mid = (left + right) / 2
            val truncated = text.substring(0, mid)
            val tokens = countTokens(truncated)
            
            if (tokens <= maxTokens) {
                bestLength = mid
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
        
        val truncatedText = text.substring(0, bestLength)
        return Pair(truncatedText, true)
    }
    
    /**
     * Обрезает текст до 2000 токенов с добавлением уведомления об обрезке
     * @param text исходный текст
     * @return пара: (обрезанный текст, было ли обрезано)
     */
    fun truncateToLimit(text: String): Pair<String, Boolean> {
        return truncateToTokens(text, 2000)
    }
}
