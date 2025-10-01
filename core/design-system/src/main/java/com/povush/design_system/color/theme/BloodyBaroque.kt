package com.povush.design_system.color.theme

import androidx.compose.ui.graphics.Color
import com.povush.design_system.color.ColorTheme
import com.povush.design_system.color.DarkColorTheme
import com.povush.design_system.color.LightColorTheme

internal object BloodyBaroque : ColorTheme {
    override val lightTheme = BloodyBaroqueLight
    override val darkTheme = BloodyBaroqueDark
}

internal object BloodyBaroqueLight : LightColorTheme {
    override val primary: Color = Color(0xFF642424)
    override val onPrimary: Color = Color(0xFFFFFF99)
    override val surface: Color = Color(0xFFECE9D8)
    override val onSurface: Color = Color(0xFF1B1717)
}

internal object BloodyBaroqueDark : DarkColorTheme {
    override val primary: Color = Color(0xFF642424)
    override val onPrimary: Color = Color(0xFFFFFF99)
    override val surface: Color = Color(0xFFECE9D8)
    override val onSurface: Color = Color(0xFF1B1717)
}
