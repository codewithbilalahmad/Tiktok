package com.muhammad.core

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
fun Modifier.rippleClickable(onClick: () -> Unit): Modifier = composed {
    Modifier.clickable(
        onClick = onClick,
        interactionSource = DisableRippleInteractionSource(),
        indication = null
    )
}