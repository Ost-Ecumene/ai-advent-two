package com.povush.quests.repository.impl

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.povush.domain.model.Quest
import com.povush.quests.repository.ChatRepository
import javax.inject.Inject

internal class ChatRepositoryImpl @Inject constructor() : ChatRepository()