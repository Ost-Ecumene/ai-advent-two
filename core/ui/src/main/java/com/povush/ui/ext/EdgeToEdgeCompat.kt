package com.povush.ui.ext

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

public fun ComponentActivity.setEdgeToEdgeConfig() {
    enableEdgeToEdge()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // Делает трёхкнопочный навигационный бар прозрачным
        // Источник: https://developer.android.com/develop/ui/views/layout/edge-to-edge#create-transparent
        window.isNavigationBarContrastEnforced = false
    }
}
