package com.povush.ui.ext

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

fun Color.toFilter(blendMode: BlendMode = BlendMode.SrcIn) = ColorFilter.tint(
    color = this,
    blendMode = blendMode
)