package com.muhammad.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.muhammad.common.ui.TiktokVerticalVideoPager
import com.muhammad.feature.home.HomeState

@Composable
fun ForYouTabSection(navHosController: NavHostController, state: HomeState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TiktokVerticalVideoPager(videos = state.shortVideos, onCommentClick = {

        }, onUserClick = {}, onAudioClick = {}, onFavouriteClick = {})
    }
}
