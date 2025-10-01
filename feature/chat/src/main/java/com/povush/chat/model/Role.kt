package com.povush.chat.model

enum class Role(val internalName: String) {
    User(internalName = "user"),
    Assistant(internalName = "assistant")
}