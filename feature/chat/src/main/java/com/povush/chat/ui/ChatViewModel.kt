package com.povush.chat.ui

import androidx.lifecycle.viewModelScope
import com.povush.common.base.BaseViewModel
import com.povush.navigation.di.AssistedVmFactory
import com.povush.navigation.route.ChatScreenRoute
import com.povush.chat.repository.ChatRepository
import com.povush.chat.ChatConfig
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ChatViewModel.Factory::class)
internal class ChatViewModel @AssistedInject constructor(
    @Assisted val navData: ChatScreenRoute,
    private val chatRepository: ChatRepository
) : BaseViewModel() {

    val chatHistory = chatRepository.chatHistory
    val input = MutableStateFlow("")
    val error = MutableStateFlow<String?>(null)
    val isStreaming = MutableStateFlow(false)
    val temperature = MutableStateFlow(ChatConfig.BASE_TEMPERATURE)
    val selectedModel = MutableStateFlow(ChatConfig.Models.BUDGET)

    fun onInputChange(value: String) {
        input.value = value
    }

    fun onTemperatureChange(value: Float) {
        temperature.value = value.toDouble()
    }
    
    fun onModelChange(model: String) {
        selectedModel.value = model
    }

    fun send() {
        val userInput = input.value.trim()
        if (userInput.isEmpty() || isStreaming.value) return
        input.value = ""
        error.value = null

        viewModelScope.launch {
            isStreaming.value = true
            try {
                chatRepository.sendRequest(userInput, temperature.value, selectedModel.value)
            } catch (t: Throwable) {
                error.value = t.message ?: "Error"
            } finally {
                isStreaming.value = false
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedVmFactory<ChatViewModel, ChatScreenRoute>
}
