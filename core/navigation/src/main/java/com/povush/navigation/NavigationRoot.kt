package com.povush.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.povush.navigation.di.EntryProviderInstaller
import com.povush.navigation.navigator.impl.NavigatorImpl
import com.povush.navigation.route.ChatScreenRoute

@Composable
public fun NavigationRoot(
    entryProviderInstallers: Set<@JvmSuppressWildcards EntryProviderInstaller>
) {
    val backStack = rememberNavBackStack(ChatScreenRoute)
    val navigator = remember(backStack) { NavigatorImpl(backStack = backStack) }

    NavDisplay(
        backStack = backStack,
        onBack = { navigator.pop() },
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entryProviderInstallers.forEach { install -> this.install(navigator) }
        }
    )
}
