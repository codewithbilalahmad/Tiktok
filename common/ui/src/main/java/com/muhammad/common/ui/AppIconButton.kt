package com.muhammad.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.muhammad.common.theme.SeparatorColor

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int,
    iconSize: Dp = 22.dp,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    shape: Shape = RoundedCornerShape(8.dp),
    height: Dp = 44.dp,
    border: BorderStroke = BorderStroke(1.dp, SeparatorColor),
    containerColor : Color = MaterialTheme.colorScheme.primary,
    contentColor : Color = MaterialTheme.colorScheme.onPrimary,
    onClick : () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier.height(height),
        shape = shape,
        border = border,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null, tint = Color.Unspecified,
                modifier = Modifier.size(iconSize)
            )
            Text(
                text = text,
                style = style,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}