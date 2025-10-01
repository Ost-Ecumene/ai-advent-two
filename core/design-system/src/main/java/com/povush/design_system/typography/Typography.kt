package com.povush.design_system.typography

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.povush.design_system.R

internal val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.carima)
        ),
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.carima)
        ),
        fontSize = 20.sp,
        lineHeight = 26.sp,
    )
)
