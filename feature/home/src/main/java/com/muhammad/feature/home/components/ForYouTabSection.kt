package com.muhammad.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.muhammad.common.ui.AppLoading
import com.muhammad.common.ui.TiktokVerticalVideoPager
import com.muhammad.feature.home.HomeState

@Composable
fun ForYouTabSection(
    state: HomeState,
    onCommentClick: (String) -> Unit,onUserClick : (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (state.isShortVideosLoading) {
            AppLoading(modifier = Modifier.align(Alignment.Center))
        } else {
            TiktokVerticalVideoPager(videos = state.shortVideos, onCommentClick = {userId ->
                onCommentClick(userId)
            }, onUserClick = {userId ->
                onUserClick(userId)
            }, onAudioClick = {}, onFavouriteClick = {})
        }
    }
}
