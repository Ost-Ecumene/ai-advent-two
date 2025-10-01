package com.povush.design_system.color

import androidx.compose.ui.graphics.Color

internal interface ColorTheme {
    val lightTheme: LightColorTheme
    val darkTheme: DarkColorTheme
}

internal interface LightColorTheme {
    val primary: Color
    val onPrimary: Color
    val surface: Color
    val onSurface: Color
}

internal interface DarkColorTheme {
    val primary: Color
    val onPrimary: Color
    val surface: Color
    val onSurface: Color
}
