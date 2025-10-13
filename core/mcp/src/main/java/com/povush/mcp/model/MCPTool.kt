package com.povush.mcp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class MCPTool(
    @Json(name = "name")
    val name: String,
    
    @Json(name = "description")
    val description: String,
    
    @Json(name = "inputSchema")
    val inputSchema: MCPInputSchema?
)

@JsonClass(generateAdapter = true)
public data class MCPInputSchema(
    @Json(name = "type")
    val type: String,
    
    @Json(name = "properties")
    val properties: Map<String, MCPProperty>?,
    
    @Json(name = "required")
    val required: List<String>?
)

@JsonClass(generateAdapter = true)
public data class MCPProperty(
    @Json(name = "type")
    val type: String,
    
    @Json(name = "description")
    val description: String?,
    
    @Json(name = "enum")
    val enum: List<String>?
)




