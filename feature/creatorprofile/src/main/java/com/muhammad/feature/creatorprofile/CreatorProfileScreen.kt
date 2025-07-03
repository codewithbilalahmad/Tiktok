package com.muhammad.feature.creatorprofile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammad.common.theme.R
import com.muhammad.common.theme.SubTextColor
import com.muhammad.common.ui.AppLoading
import com.muhammad.feature.creatorprofile.components.ProfileDetailSection
import com.muhammad.feature.creatorprofile.components.ProfileShortVideoItem
import com.muhammad.feature.creatorprofile.components.ProfileTabSection
import com.muhammad.feature.creatorprofile.components.ProfileTopBar
import com.muhammad.feature.creatorprofile.components.ProfileVideosScreen
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatorProfileScreen(
    viewModel: CreatorProfileViewModel = koinViewModel(),
    onBack: () -> Unit,
) {
    val listState = rememberLazyListState()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val userData = state.user
    val isLoading = state.isLoading
    val userShortVideos = state.userShortVideos
    val tabs = ProfileTabs.entries
    val pagerState = rememberPagerState { tabs.size }
    LaunchedEffect(listState, pagerState.currentPage) {
        if (pagerState.currentPage == 0) {
            snapshotFlow { listState.firstVisibleItemIndex to listState.layoutInfo.totalItemsCount }.distinctUntilChanged()
                .collect { (firstVisibleItemIndex, totalCount) ->
                    if (firstVisibleItemIndex + 6 >= totalCount) {
                        viewModel.onEvent(ProfileEvent.PaginateUserShortVideos)
                    }
                }
        }
    }
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AppLoading(Modifier)
        }
    } else {
        userData?.let { user ->
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                ProfileTopBar(
                    fullname = userData.fullname,
                    onMoreClick = {},
                    onViewsClick = {},
                    onBack = onBack
                )
            }) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues), state = listState
                ) {
                    item {
                        ProfileDetailSection(
                            modifier = Modifier.fillMaxWidth(),
                            user = user,
                            onFollowerClick = {},
                            onViewsClick = {},
                            onVideosClick = {})
                    }
                    stickyHeader {
                        ProfileTabSection(pagerState = pagerState, pages = tabs)
                    }
                    item {
                        HorizontalPager(
                            state = pagerState, pageSpacing = 8.dp,
                            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top
                        ) { page ->
                            when (page) {
                                0 -> {
                                    if (isLoading) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            AppLoading(Modifier)
                                        }
                                    } else {
                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            Spacer(Modifier.height(6.dp))
                                            userShortVideos.chunked(3).forEach { videos ->
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    videos.forEachIndexed { indexInRow, video ->
                                                        Box(
                                                            modifier = Modifier
                                                                .weight(1f)
                                                                .aspectRatio(1f)
                                                        ) {
                                                            val index = videos.indexOf(video)
                                                            ProfileShortVideoItem(
                                                                modifier = Modifier.fillMaxSize(),
                                                                video = video, onClick = {
                                                                    viewModel.onEvent(ProfileEvent.OnProfileVideoClick(index))
                                                                    viewModel.onEvent(ProfileEvent.ToggleProfileVideoSection)
                                                                }
                                                            )
                                                        }
                                                    }
                                                    if (videos.size < 3) {
                                                        videos.forEachIndexed{rowInIndex, video ->
                                                            Box(
                                                                modifier = Modifier
                                                                    .weight(1f)
                                                                    .aspectRatio(1f)
                                                            ) {
                                                                val index = videos.indexOf(video)
                                                                ProfileShortVideoItem(
                                                                    modifier = Modifier.fillMaxSize(),
                                                                    video = video, onClick = {
                                                                        viewModel.onEvent(ProfileEvent.OnProfileVideoClick(index))
                                                                        viewModel.onEvent(ProfileEvent.ToggleProfileVideoSection)
                                                                    }
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (state.isMoreUserShortVideoLoading) {
                                                Spacer(Modifier.height(8.dp))
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    AppLoading(Modifier)
                                                }
                                            }
                                        }
                                    }
                                }

                                1 -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 30.dp, horizontal = 20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.this_users_liked_videos),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            text = "${stringResource(R.string.videos_liked_by)} ${user.fullname} ${
                                                stringResource(
                                                    R.string.currently_hidden
                                                )
                                            }",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = SubTextColor,
                                                textAlign = TextAlign.Center
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    AnimatedVisibility(
        state.showProfileVideosSection,
        enter = slideInHorizontally { -it },
        exit = slideOutHorizontally { it }, modifier = Modifier.fillMaxSize()
    ) {
        ProfileVideosScreen(
            videos = state.userShortVideos,
            index = state.selectedProfileVideosIndex ?: 0,
            onUserClick = {},
            onBack = {
                viewModel.onEvent(ProfileEvent.ToggleProfileVideoSection)
            })
    }
}