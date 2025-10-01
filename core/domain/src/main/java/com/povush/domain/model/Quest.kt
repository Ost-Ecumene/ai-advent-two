package com.povush.domain.model

public data class Quest(
    val id: String,
    val typeRaw: Int,
    val statusRaw: Int,
    val title: String,
    val description: String,
    val difficultyRaw: Int,
    val tasks: List<Task>,
    val completionDateTimestamp: String?
) {
    val type: QuestType get() = QuestType.entries[typeRaw]
    val status: QuestStatus get() = QuestStatus.entries[statusRaw]
    val difficulty: QuestDifficulty get() = QuestDifficulty.entries[difficultyRaw]
}
