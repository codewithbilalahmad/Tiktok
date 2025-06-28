package com.muhammad.common.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppLoading() {
    val infiniteTransition = rememberInfiniteTransition()
    val dotRed by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing)
        ), label = "dotRed"
    )
    val dotCyan by infiniteTransition.animateFloat(
        initialValue = 180f, targetValue = 540f, animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing)
        ), label = "dotCyan"
    )
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier
            .size(12.dp)
            .graphicsLayer {
                rotationX = dotRed
                cameraDistance = 8 * density
            }
            .background(Color.Red, CircleShape))
        Box(modifier = Modifier
            .size(12.dp)
            .graphicsLayer {
                rotationX = dotCyan
                cameraDistance = 8 * density
            }
            .background(Color.Cyan, CircleShape))
    }
}

@Preview(showBackground = true)
@Composable
private fun AppLoadingPreview() {
    AppLoading()
}