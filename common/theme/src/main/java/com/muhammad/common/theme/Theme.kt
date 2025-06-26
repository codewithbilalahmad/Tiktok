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
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    secondary = PrimaryColor,
    onSecondary = Color.White,
    background = White,
    onBackground = Black,
    surface = White,
    surfaceTint = White,
    onSurface = Black,
)
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
fun TiktokTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    val view = LocalView.current
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme
    val window = (view.context as Activity).window
    if (!view.isInEditMode) {
        SideEffect {
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