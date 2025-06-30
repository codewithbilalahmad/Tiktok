package com.muhammad.common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.muhammad.common.theme.Gray20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    showBottomSheet: Boolean,
    sheetState: SheetState,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onDismiss: () -> Unit,shape: Shape = RoundedCornerShape(0.dp),
    content: @Composable () -> Unit,
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = modifier
                .statusBarsPadding(),
            sheetState = sheetState, containerColor = Gray20,
            dragHandle = {

            },
            onDismissRequest = onDismiss,
            shape = shape
        ) {
            content()
        }
    }
}
