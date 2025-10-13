package com.povush.mcp.api

import com.povush.mcp.model.MCPExecuteRequest
import com.povush.mcp.model.MCPExecuteResponse
import com.povush.mcp.model.MCPToolsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MCPService {
    @GET("/api/tools")
    suspend fun getTools(): MCPToolsResponse
    
    @POST("/api/tools/execute")
    suspend fun executeTool(@Body request: MCPExecuteRequest): MCPExecuteResponse
}
