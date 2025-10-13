package com.povush.mcp.di

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entry
import com.povush.mcp.repository.MCPRepository
import com.povush.mcp.ui.MCPScreen
import com.povush.navigation.di.EntryProviderInstaller
import com.povush.navigation.route.ChatScreenRoute
import com.povush.navigation.route.MCPScreenRoute
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MCPNavigatorModule {
    
    @Provides
    @IntoSet
    fun provideMCPEntryProviderInstaller(
        mcpRepository: MCPRepository
    ): EntryProviderInstaller = {
        entry<MCPScreenRoute> {
            MCPScreen(mcpRepository = mcpRepository)
        }
    }
}
