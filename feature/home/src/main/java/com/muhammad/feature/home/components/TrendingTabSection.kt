package com.muhammad.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muhammad.common.theme.R
import com.muhammad.common.theme.SubTextColor
import com.muhammad.common.ui.AppLoading
import com.muhammad.feature.home.HomeEvent
import com.muhammad.feature.home.HomeState

@Composable
fun TrendingTabSection(
    state: HomeState,
    onAction: (HomeEvent) -> Unit,onUserClick : (String) -> Unit
) {
    val videos = state.trendingVideos
    val isLoading = state.isTrendingShortVideosLoading
    val pagerState = rememberPagerState { videos.size }
    Column(
        modifier = Modifier
            .fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(80.dp))
        Text(
            text = stringResource(R.string.trending_creators),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.follow_and_account_to_see),
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center,
                color = SubTextColor
            )
        )
        if (isLoading) {
            AppLoading(modifier = Modifier)
        } else {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 54.dp),
                beyondViewportPageCount = 1
            ) { index ->
                CreatorCard(
                    index = index,
                    video = videos[index],
                    onUserClick = {userId ->
                        onUserClick(userId)
                    },
                    onFollowClick = {},
                    pagerState = pagerState
                )
                if (index == videos.lastIndex -1 && state.isMoreTrendingShortVideosLoading) {
                    Box(
                        modifier = Modifier.width(200.dp).height(350.dp)
                            .clip(RoundedCornerShape(8.dp)).background(Color.Black.copy(0.59f)),contentAlignment = Alignment.Center
                    ){
                        AppLoading(modifier = Modifier)
                    }
                }
            }
            LaunchedEffect(pagerState.currentPage) {
                if(pagerState.currentPage >= videos.lastIndex -1 && !state.isMoreTrendingShortVideosLoading){
                    onAction(HomeEvent.OnPaginateTrendingShortVideos)
                }
            }
        }
    }
}