package com.povush.mcp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class MCPToolsResponse(
    @Json(name = "tools")
    val tools: List<MCPTool>
)

@JsonClass(generateAdapter = true)
public data class MCPExecuteResponse(
    @Json(name = "status")
    val status: String,
    
    @Json(name = "result")
    val result: String?,
    
    @Json(name = "error")
    val error: String?
)

@JsonClass(generateAdapter = true)
public data class MCPExecuteRequest(
    @Json(name = "toolName")
    val toolName: String,
    
    @Json(name = "arguments")
    val arguments: Map<String, Any>
)




