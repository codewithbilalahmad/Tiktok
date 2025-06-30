package com.muhammad.common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppDivider(
    modifier: Modifier = Modifier.fillMaxWidth(),
    thickness: Dp = 0.2.dp,
    color: Color = MaterialTheme.colorScheme.onBackground,
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness, color =color
    )
}