package com.povush.quests.ui

import com.povush.common.base.BaseViewModel
import com.povush.navigation.di.AssistedVmFactory
import com.povush.navigation.route.ChatScreenRoute
import com.povush.quests.repository.ChatRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ChatViewModel.Factory::class)
internal class ChatViewModel @AssistedInject constructor(
    @Assisted val navData: ChatScreenRoute,
    private val chatRepository: ChatRepository
) : BaseViewModel() {

    @AssistedFactory
    interface Factory : AssistedVmFactory<ChatViewModel, ChatScreenRoute>
}
