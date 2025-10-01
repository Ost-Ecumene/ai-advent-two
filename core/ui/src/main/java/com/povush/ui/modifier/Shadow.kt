package com.povush.ui.modifier

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @param offsetX Сдвиг по горизонтали
 * @param offsetY Сдвиг по вертикали
 * @param blur Размер тени
 * @param spread Выход тени за пределы края компонента
 * @param color Цвет тени
 * @param borderRadius Скругление всех углов
 */
public fun Modifier.drawShadow(
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blur: Dp = 0.dp,
    spread: Dp = 0.dp,
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
) = drawBehind {
    drawIntoCanvas {
        val spreadPixel = spread.toPx()
        val leftPixel = offsetX.toPx() - spreadPixel
        val topPixel = offsetY.toPx() - spreadPixel
        val rightPixel = this.size.width + spreadPixel
        val bottomPixel = this.size.height + spreadPixel

        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()

        if (blur != 0.dp) {
            frameworkPaint.maskFilter = BlurMaskFilter(
                blur.toPx(),
                BlurMaskFilter.Blur.OUTER
            )
        }
        frameworkPaint.color = color.toArgb()

        it.drawRoundRect(
            left = leftPixel,
            top = topPixel,
            right = rightPixel,
            bottom = bottomPixel,
            radiusX = borderRadius.toPx(),
            radiusY = borderRadius.toPx(),
            paint
        )
    }
}
