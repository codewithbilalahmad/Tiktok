package com.muhammad.feature.loginwithemailphone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammad.common.theme.R
import com.muhammad.common.theme.SeparatorColor
import com.muhammad.common.theme.SubTextColor
import com.muhammad.common.ui.AppTopBar
import com.muhammad.feature.loginwithemailphone.components.EmailUsernameTabScreen
import com.muhammad.feature.loginwithemailphone.components.PhoneTabScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginWithEmailPhoneScreen(viewModel: LoginViewModel = koinViewModel(), onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pages = LoginPages.entries
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            viewModel.onEvent(LoginEvent.OnPageChange(page))
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            AppTopBar(title = stringResource(R.string.login_or_sign_up), actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_question_circle),
                        contentDescription = null,
                    )
                }
            }, showNavigationIcon = true, onBack = {
                onBack()
            })
            TabRow(selectedTabIndex = pagerState.currentPage, indicator = { tabPositions ->
                val modifier =
                    Modifier
                        .clip(CircleShape)
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .padding(horizontal = 26.dp)
                TabRowDefaults.PrimaryIndicator(modifier = modifier)
            }, divider = {
                HorizontalDivider(thickness = 0.5.dp, color = SeparatorColor)
            }) {
                pages.forEachIndexed { index, item ->
                    val isSelected = pagerState.currentPage == index
                    Tab(
                        selected = isSelected,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = SubTextColor,
                        text = {
                            Text(
                                text = stringResource(item.title),
                                style = MaterialTheme.typography.bodySmall
                            )
                        })
                }
            }
        }
    }) { paddingValues ->
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = pagerState
        ) { page ->
            when(page) {
                0 -> PhoneTabScreen(state = state)
                1 -> EmailUsernameTabScreen(state = state)
            }
        }
    }
}