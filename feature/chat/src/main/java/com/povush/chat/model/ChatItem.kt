package com.povush.chat.model

import com.povush.chat.network.dto.QuestDto

sealed class ChatItem {
    data class Message(
        val text: String,
        val role: Role
    ) : ChatItem()

    data class Quest(
        val quest: QuestDto
    ) : ChatItem()

    data class Log(
        val text: String,
        val role: Role
    ) : ChatItem()
}