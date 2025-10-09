package com.povush.chat.model

/**
 * Модель задачи для взаимодействия агентов
 */
data class AgentTask(
    val userTask: String,              // Исходное задание от пользователя
    val writerResponse: String,        // Ответ Агента-Писателя
    val reviewerResponse: String,      // Ответ Агента-Ревьюера
    val writerMetrics: MessageMetrics? = null,   // Метрики работы писателя
    val reviewerMetrics: MessageMetrics? = null  // Метрики работы ревьюера
)

/**
 * Состояние выполнения задачи агентами
 */
enum class AgentTaskStatus {
    PENDING,           // Ожидает выполнения
    WRITER_WORKING,    // Агент-Писатель работает
    REVIEWER_WORKING,  // Агент-Ревьюер работает  
    COMPLETED,         // Выполнено
    ERROR              // Ошибка
}

