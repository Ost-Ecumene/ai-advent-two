package com.povush.mcp.di

import com.povush.mcp.api.MCPClient
import com.povush.mcp.repository.MCPRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MCPModule {
    
    @Provides
    @Singleton
    fun provideMCPClient(): MCPClient = MCPClient()
    
    @Provides
    @Singleton
    fun provideMCPRepository(mcpClient: MCPClient): MCPRepository = 
        MCPRepository(mcpClient)
}




