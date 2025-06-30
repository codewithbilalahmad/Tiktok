package com.muhammad.common.ui

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.muhammad.common.theme.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    showNavigationIcon: Boolean = false,
    title: String,
    background: Color = Color.Transparent,
    actions: @Composable () -> Unit = {},
    onBack: () -> Unit = {},
) {
    CenterAlignedTopAppBar(title = {
        Text(text = title, style= MaterialTheme.typography.headlineMedium)
    }, navigationIcon = {
        if(showNavigationIcon){
            IconButton(onClick = {
                onBack()
            }) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back), contentDescription = null)
            }
        }
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = background), actions = {
        actions()
    })
}