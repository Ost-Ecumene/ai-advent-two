package com.povush.domain.model

public data class Task(
    val text: String,
    val statusRaw: Int,
    val counter: TaskCounter?,
    val subtasks: List<Task>
) {
    val status: TaskStatus get() = TaskStatus.entries[statusRaw]
}
