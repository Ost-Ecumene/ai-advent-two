package com.povush.navigation.di

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import com.povush.navigation.navigator.Navigator

/**
 * Установщик entry в NavDisplay, поставляемый из DI-модулей отдельных фич.
 */
public typealias EntryProviderInstaller = EntryProviderBuilder<NavKey>.(Navigator) -> Unit
