package com.povush.chat.repository

import com.povush.chat.model.ChatItem
import com.povush.common.base.BaseRepository
import kotlinx.coroutines.flow.StateFlow

internal abstract class ChatRepository : BaseRepository() {
    abstract val chatHistory: StateFlow<List<ChatItem>>
    abstract suspend fun sendRequest(message: String)
}