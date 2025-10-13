package com.povush.mcp.repository

import android.util.Log
import com.povush.mcp.model.MCPExecuteRequest
import com.povush.mcp.model.MCPExecuteResponse
import com.povush.mcp.model.MCPTool
import com.povush.mcp.api.MCPClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MCPRepository @Inject constructor(
    private val mcpClient: MCPClient
) {
    
    suspend fun getAvailableTools(): List<MCPTool> {
        return try {
            Log.d("MCP", "Запрос списка инструментов...")
            val response = mcpClient.service.getTools()
            Log.d("MCP", "Получен ответ: ${response.tools.size} инструментов")
            response.tools.forEach { tool ->
                Log.d("MCP", "Инструмент: ${tool.name} - ${tool.description}")
            }
            response.tools
        } catch (e: Exception) {
            Log.e("MCP", "Ошибка получения инструментов", e)
            emptyList()
        }
    }
    
    suspend fun executeTool(toolName: String, arguments: Map<String, Any>): MCPExecuteResponse {
        return try {
            val request = MCPExecuteRequest(
                toolName = toolName,
                arguments = arguments
            )
            mcpClient.service.executeTool(request)
        } catch (e: Exception) {
            MCPExecuteResponse(
                status = "error",
                result = null,
                error = e.message
            )
        }
    }
}
