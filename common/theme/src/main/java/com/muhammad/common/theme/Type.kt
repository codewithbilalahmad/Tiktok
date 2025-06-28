package com.muhammad.common.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val fontFamily = FontFamily(
    Font(R.font.proximanova)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 32.sp,color = White,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 24.sp,color = White,
        fontWeight = FontWeight.Bold
    ),

    headlineLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 27.sp,color = White,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Bold
    ),

    headlineMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 17.sp,
        lineHeight = 18.sp,color = White,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontSize = 20.sp,
        fontFamily = fontFamily,color = White,
        fontWeight = FontWeight.Bold
    ),

    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = fontFamily,color = White,
        fontWeight = FontWeight.Bold
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 15.sp,color = White,
        fontWeight = FontWeight.Bold
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        color = White,
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = White,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        color = White,
    ),

    labelLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 15.sp,
        color = White,
        fontWeight = FontWeight.SemiBold
    ),

    labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 13.sp,
        color = White,
        fontWeight = FontWeight.SemiBold
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 10.sp,
        color = White,
        fontWeight = FontWeight.SemiBold
    )

)