package com.muhammad.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammad.common.theme.DarkBlue
import com.muhammad.common.theme.R
import com.muhammad.common.theme.SubTextColor
import com.muhammad.common.ui.AppTopBar
import com.muhammad.core.rippleClickable
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    onBack: () -> Unit,
    onAccountClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val isCollapsed by remember {
        derivedStateOf {
            scrollState.value.dp > 132.dp
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AppTopBar(
            title = if (isCollapsed) stringResource(com.muhammad.common.theme.R.string.settings_and_privacy) else "",
            showNavigationIcon = true,
            onBack = {
                onBack()
            })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(R.string.settings_and_privacy),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
            )
            Spacer(Modifier.height(20.dp))
            state.settingUiModel.forEach { group ->
                GroupedSettingSection(item = group.key to group.value, onClick = { titleId ->
                    when (titleId) {
                        R.string.my_account -> onAccountClick()
                    }
                })
            }
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Version 1.0",
                style = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun GroupedSettingSection(item: Pair<String, List<SettingItem>>, onClick: (Int) -> Unit) {
    Text(
        text = item.first,
        modifier = Modifier.padding(horizontal = 20.dp),
        style = MaterialTheme.typography.labelLarge.copy(color = SubTextColor)
    )
    Spacer(Modifier.height(20.dp))
    Card(
        modifier = Modifier.padding(horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkBlue)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            item.second.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .rippleClickable {
                            onClick(item.title)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = stringResource(item.title), modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (item.title == R.string.my_account) {
                        Text(
                            text = stringResource(R.string.sign_up),
                            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 20.dp, vertical = 6.dp)
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_right_arrow),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
    Spacer(Modifier.height(20.dp))
}