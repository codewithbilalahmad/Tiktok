package com.muhammad.feature.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.muhammad.common.theme.R
import com.muhammad.common.theme.WhiteAlpha95
import com.muhammad.common.ui.VideoPlayer
import com.muhammad.data.domain.model.Video
import kotlin.math.absoluteValue

@Composable
fun CreatorCard(
    index: Int,
    pagerState: PagerState,
    video: Video,
    onFollowClick: (String) -> Unit,
    onUserClick: (String) -> Unit,
) {
    val pageOffset =
        ((pagerState.currentPage - index) + (pagerState.currentPageOffsetFraction)).absoluteValue
    Card(modifier = Modifier.graphicsLayer {
        lerp(start = 0.9f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)).also { scale ->
            scaleX = scale
            scaleY = scale
        }
    }, shape = RoundedCornerShape(8.dp)) {
        Box(
            modifier = Modifier
                .drawWithContent {
                    drawContent()
                    val color =
                        lerp(
                            Color.Black.copy(0.59f),
                            Color.Transparent,
                            pageOffset.coerceIn(0f, 1f)
                        )
                    drawRect(color)
                }
                .height(340.dp)) {
            VideoPlayer(
                video = video,
                pagerState = pagerState,
                onSingleTap = { exoPlayer ->
                    onUserClick(video.id)
                },
                onDoubleTap = { exoPlayer, offset ->

                },
                onVideoDispose = {},
                onVideoGoBackground = {},
                isLoading = {},
                modifier = Modifier.fillMaxSize(),
                pageIndex = index
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .padding(12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter).padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                AsyncImage(
                    model = video.authorDetails.profileImage,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .size(70.dp)
                        .border(
                            BorderStroke(width = 1.dp, color = Color.White),
                            CircleShape
                        )
                        .clip(CircleShape), contentScale = ContentScale.Crop
                )
                Text(
                    text = video.authorDetails.username,
                    style = MaterialTheme.typography.labelMedium.copy(color = Color.White)
                )
                Text(
                    text = "@${video.authorDetails.username.lowercase()}",
                    style = MaterialTheme.typography.labelMedium.copy(color = WhiteAlpha95)
                )
                Button(
                    onClick = {
                        onFollowClick(video.id)
                    },
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .padding(horizontal = 36.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.follow))
                }
            }
        }
    }
}