@file:Suppress("DEPRECATION")

package com.muhammad.common.theme

import android.app.Activity
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    secondary = PrimaryColor,
    onSecondary = Color.White,
    background = Black,
    onBackground = White,
    surface = Black,
    surfaceTint = Black,
    onSurface = White
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TiktokTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    val colorScheme =darkColorScheme
    val window = (view.context as Activity).window
    if (!view.isInEditMode) {
        SideEffect {
            window.navigationBarColor = Black.toArgb()
            window.statusBarColor = Black.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }
    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        typography = Typography,
        motionScheme = MotionScheme.expressive(),
        content = content
    )
}