package com.muhammad.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammad.common.theme.R
import com.muhammad.core.rippleClickable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CommentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onEmojiOrKeyboardToggle: () -> Unit, showSendButton: Boolean,
    onTagClick: () -> Unit, showTrailingSection: Boolean,
    modifier: Modifier = Modifier,
    maxLines: Int = 4, onSendComment: () -> Unit,
    hint: String = "Add comment...", isKeyboardShow: Boolean,
    shape: Shape = CircleShape, focusRequester: FocusRequester,
    style: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, shape)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = style,
            modifier = Modifier
                .focusRequester(focusRequester)
                .weight(1f)
                .padding(4.dp),
            maxLines = maxLines,
            singleLine = false,
            decorationBox = { innerTextField ->
                if (value.isBlank()) {
                    Text(
                        text = hint,
                        style = style.copy(lineHeight = 18.sp)
                    )
                }
                innerTextField()
            },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
        if (showTrailingSection) {
            Spacer(Modifier.width(4.dp))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_email),
                contentDescription = null, modifier = Modifier.rippleClickable {
                    onTagClick()
                },
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = ImageVector.vectorResource(if (isKeyboardShow) R.drawable.ic_keyboard else R.drawable.ic_emoji),
                contentDescription = null, modifier = Modifier.rippleClickable {
                    onEmojiOrKeyboardToggle()
                },
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else if (showSendButton) {
            FilledTonalIconButton(
                onClick = {
                    onSendComment()
                }, colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f)
                ), shape = CircleShape, modifier = Modifier.size(
                    IconButtonDefaults.smallContainerSize(
                        IconButtonDefaults.IconButtonWidthOption.Narrow
                    )
                )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_right_arrow),
                    contentDescription = null, modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}