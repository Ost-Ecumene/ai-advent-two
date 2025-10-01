package com.povush.aiadvent2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.povush.design_system.AiAdventTheme
import com.povush.navigation.NavigationRoot
import com.povush.navigation.di.EntryProviderInstaller
import com.povush.ui.ext.setEdgeToEdgeConfig
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var entryProviderInstallers: Set<@JvmSuppressWildcards EntryProviderInstaller>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeConfig()
        setContent {
            AiAdventTheme {
                NavigationRoot(entryProviderInstallers)
            }
        }
    }
}