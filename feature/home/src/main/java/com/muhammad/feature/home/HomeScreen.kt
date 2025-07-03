package com.muhammad.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammad.common.theme.R
import com.muhammad.common.theme.White
import com.muhammad.feature.home.components.CommentsBottomSheet
import com.muhammad.feature.home.components.ForYouTabSection
import com.muhammad.feature.home.components.TrendingTabSection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun HomeScreen(viewModel: HomeViewModel,onUserClick:(String)->Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tabItems = arrayListOf(R.string.trending, R.string.for_you)
    val pagerState = rememberPagerState(initialPage = 1) { tabItems.size }
    val scope = rememberCoroutineScope()
    val edge = LocalConfiguration.current.screenWidthDp.dp.div(2).minus(100.dp)
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState) { index ->
            when (index) {
                0 -> TrendingTabSection(state = state, onAction = viewModel::onEvent, onUserClick = onUserClick)
                1 -> ForYouTabSection(
                    state = state,
                    onCommentClick = { videoId ->
                        viewModel.onEvent(HomeEvent.OnToggleCommentBottomSheet)
                        viewModel.onEvent(HomeEvent.LoadComments(videoId = videoId))
                    }, onUserClick = onUserClick)
            }
        }
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            divider = {},
            modifier = Modifier.padding(top = 8.dp),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .padding(horizontal = 38.dp)
                        .clip(CircleShape)
                )
            }, edgePadding = edge
        ) {
            tabItems.forEachIndexed { index, item ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    selected = isSelected,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        val textStyle = if (isSelected) {
                            MaterialTheme.typography.titleMedium.copy(color = Color.White)
                        } else {
                            MaterialTheme.typography.titleMedium.copy(
                                color = White.copy(alpha = 0.6f),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Text(text = stringResource(id = item), style = textStyle)
                    }
                )
            }
        }
    }
    CommentsBottomSheet(
        showCommentBottomSheet = state.showCommentBottomSheet,
        commentsList = state.comments,
        onAction = viewModel::onEvent,
        onDismiss = {
            viewModel.onEvent(HomeEvent.OnToggleCommentBottomSheet)
        },
        comment = state.comment,
        isCommentLoading = state.isCommentsLoading,
        isMoreCommentLoading = state.isMoreCommentsLoading
    )
}