package com.povush.design_system

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.povush.design_system.color.AppThemes
import com.povush.design_system.color.CommonDark
import com.povush.design_system.color.CommonLight
import com.povush.design_system.typography.Typography

@Composable
public fun AiAdventTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val currentTheme = AppThemes[0]
//
//    val colorScheme = when {
//        darkTheme -> {
//            with(currentTheme.darkTheme) {
//                darkColorScheme(
//                    primary = primary,
//                    onPrimary = onPrimary,
//                    surface = surface,
//                    onSurface = onSurface,
//                    background = CommonLight.background,
//                    onBackground = CommonLight.onBackground,
//                    error = CommonLight.error,
//                    outline = CommonLight.outline
//                )
//            }
//        }
//        else -> {
//            with(currentTheme.lightTheme) {
//                lightColorScheme(
//                    primary = primary,
//                    onPrimary = onPrimary,
//                    surface = surface,
//                    onSurface = onSurface,
//                    background = CommonDark.background,
//                    onBackground = CommonDark.onBackground,
//                    error = CommonDark.error,
//                    outline = CommonDark.outline
//                )
//            }
//        }
//    }

    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme(),
        typography = Typography,
        content = content
    )
}
