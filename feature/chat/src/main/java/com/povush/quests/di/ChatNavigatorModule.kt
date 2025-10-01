package com.povush.quests.di

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entry
import com.povush.navigation.di.EntryProviderInstaller
import com.povush.navigation.route.ChatScreenRoute
import com.povush.quests.ui.ChatScreen
import com.povush.quests.ui.ChatViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
internal class ChatNavigatorModule {

    @IntoSet
    @Provides
    fun provideQuestsScreen(): EntryProviderInstaller = { navigator ->
        entry<ChatScreenRoute> { navData ->
            val vm = hiltViewModel<ChatViewModel, ChatViewModel.Factory> { it.create(navData) }
            ChatScreen(
                navigator = navigator,
                viewModel = vm
            )
        }
    }
}
