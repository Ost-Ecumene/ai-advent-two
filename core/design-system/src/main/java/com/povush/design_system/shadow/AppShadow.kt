package com.povush.design_system.shadow

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow

public object AppShadow {
    public val textShadow: Shadow = Shadow(
        color = Color.Black,
        offset = Offset(x = 1.5f, y = 1.5f),
        blurRadius = 3f
    )
}
