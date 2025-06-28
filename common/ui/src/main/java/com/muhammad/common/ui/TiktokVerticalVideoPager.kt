package com.muhammad.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muhammad.common.theme.Gray20
import com.muhammad.common.theme.GrayLight
import com.muhammad.common.theme.R
import com.muhammad.core.DisableRippleInteractionSource
import com.muhammad.core.IntentUtils.share
import com.muhammad.core.rippleClickable
import com.muhammad.data.domain.model.Video
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TiktokVerticalVideoPager(
    modifier: Modifier = Modifier,
    videos: List<Video>,
    initialPage: Int? = 0,
    showUploadDate: Boolean = false,
    onCommentClick: (String) -> Unit,
    onFavouriteClick: () -> Unit,
    onAudioClick: (Video) -> Unit,
    onUserClick: (String) -> Unit,
) {
    var isLoading by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(initialPage = initialPage ?: 0, pageCount = { videos.size })
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val flingBehavior = PagerDefaults.flingBehavior(
        state = pagerState, snapAnimationSpec = tween(easing = LinearEasing, durationMillis = 300)
    )
    VerticalPager(
        state = pagerState,
        flingBehavior = flingBehavior,
        beyondViewportPageCount = 1,
        modifier = modifier
    ) { index ->
        var pauseButtonVisible by remember { mutableStateOf(false) }
        var doubleTabState by remember { mutableStateOf(Triple(Offset.Unspecified, false, 0f)) }
        val video = videos[index]
        Box(modifier = Modifier.fillMaxSize()) {
            VideoPlayer(video = video, pagerState = pagerState, onSingleTap = { exoPlayer ->
                pauseButtonVisible = exoPlayer.isPlaying
                exoPlayer.playWhenReady = !exoPlayer.isPlaying
            }, onDoubleTap = { exoPlayer, offset ->
                scope.launch {
                    video.currentViewInteraction.isLikedByYou = true
                    val rotationAngle = (-10..10).random()
                    doubleTabState = Triple(offset, true, rotationAngle.toFloat())
                    delay(1000)
                    doubleTabState = Triple(offset, false, rotationAngle.toFloat())
                }
            }, onVideoDispose = {
                pauseButtonVisible = false
            }, onVideoGoBackground = {
                pauseButtonVisible = false
            }, pageIndex = index, modifier = Modifier.fillMaxSize(), isLoading = { loading ->
                isLoading = loading
            })
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    VideoFooter(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        video = video,
                        showUploadDate = showUploadDate,
                        onAudioClick = onAudioClick, onUserClick = onUserClick
                    )
                    VideoDetails(
                        modifier = Modifier,
                        video = video,
                        doubleTabState = doubleTabState,
                        onCommentClick = onCommentClick,
                        onUserClick = onUserClick,
                        onFavouriteClick = onFavouriteClick,
                    )
                }
            }
            AnimatedVisibility(
                visible = pauseButtonVisible,
                enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f),
                exit = scaleOut(tween(150)), modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_play),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
            AnimatedVisibility(
                visible = isLoading,
                enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f),
                exit = scaleOut(tween(150)), modifier = Modifier.align(Alignment.Center)
            ) {
                AppLoading(modifier = Modifier)
            }
            AnimatedVisibility(
                visible = doubleTabState.second,
                enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.3f),
                exit = scaleOut(
                    tween(600),
                    targetScale = 1.58f
                ) + fadeOut(tween(600)) + slideOutVertically(tween(600)), modifier = Modifier.then(
                    if (doubleTabState.first != Offset.Unspecified) {
                        Modifier.offset(x = with(density) {
                            doubleTabState.first.x.toDp().plus(-110.dp.div(2))
                        }, y = with(density) { doubleTabState.first.y.toDp().plus(-110.dp.div(2)) })
                    } else Modifier
                )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_like),
                    contentDescription = null,
                    tint = if (doubleTabState.second) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary.copy(
                        0.8f
                    ),
                    modifier = Modifier
                        .size(110.dp)
                        .rotate(doubleTabState.third)
                )
            }
        }
    }
}

@Composable
fun VideoDetails(
    modifier: Modifier,
    video: Video,
    doubleTabState: Triple<Offset, Boolean, Float>,
    onCommentClick: (String) -> Unit,
    onFavouriteClick: () -> Unit,
    onUserClick: (String) -> Unit,
) {
    val context = LocalContext.current
    var isLiked by remember { mutableStateOf(video.currentViewInteraction.isLikedByYou) }
    LaunchedEffect(doubleTabState) {
        if (doubleTabState.first != Offset.Unspecified && doubleTabState.second) {
            isLiked = true
        }
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = video.authorDetails.profileImage,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .border(
                    BorderStroke(1.dp, color = Color.White), shape = CircleShape
                )
                .clip(CircleShape)
                .rippleClickable {
                    onUserClick(video.authorDetails.userId)
                }, contentScale = ContentScale.Crop
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_add),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .offset(y = -(8).dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.primary
                )
                .padding(6.dp)
        )
        Spacer(Modifier.height(12.dp))
        LikeButton(isLiked = isLiked, onLiked = { liked ->
            isLiked = liked
            video.currentViewInteraction.isLikedByYou = liked
        }, likeCount = video.videoStats.formattedLikeCount)
        VideoAction(
            icon = R.drawable.ic_comment,
            title = video.videoStats.formattedCommentCount,
            onClick = {
                onCommentClick(video.id)
            })
        Spacer(Modifier.height(16.dp))
        VideoAction(
            icon = R.drawable.ic_bookmark,
            title = video.videoStats.formattedFavouriteCount,
            onClick = {
                onFavouriteClick()
            })
        Spacer(Modifier.height(14.dp))
        VideoAction(
            icon = R.drawable.ic_share,
            title = video.videoStats.formattedShareCount,
            onClick = {
                context.share(text = video.description, title = "Tiktok Share")
            })
        Spacer(Modifier.height(20.dp))
        RotationAudioView(video.authorDetails.profileImage)
    }
}

@Composable
fun VideoAction(icon: Int, title: String, onClick: () -> Unit) {
    IconButton(interactionSource = DisableRippleInteractionSource(), onClick = {
        onClick()
    }) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = null, tint = Color.White,
            modifier = Modifier.size(33.dp)
        )
    }
    Text(text = title, style = MaterialTheme.typography.labelMedium)
}

@Composable
fun LikeButton(isLiked: Boolean, likeCount: String, onLiked: (Boolean) -> Unit) {
    val size by animateDpAsState(
        targetValue = if (isLiked) 33.dp else 32.dp, animationSpec = keyframes {
            durationMillis = 400
            24.dp.at(50)
            38.dp.at(190)
            26.dp.at(330)
            32.dp.at(400).with(FastOutLinearInEasing)
        }
    )
    IconButton(onClick = {
        onLiked(!isLiked)
    }, interactionSource = DisableRippleInteractionSource(), modifier = Modifier.size(38.dp)) {
        val tint = if (isLiked) MaterialTheme.colorScheme.primary else Color.White
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_heart),
            contentDescription = null,
            tint = tint, modifier = Modifier.size(size)
        )
    }
    Text(text = likeCount, style = MaterialTheme.typography.labelMedium)
    Spacer(Modifier.height(16.dp))
}

@Composable
fun VideoFooter(
    modifier: Modifier,
    video: Video,
    showUploadDate: Boolean,
    onAudioClick: (Video) -> Unit,
    onUserClick: (String) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        Row(
            modifier = Modifier.rippleClickable {
                onUserClick(video.authorDetails.userId)
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = video.authorDetails.username, style = MaterialTheme.typography.bodyMedium)
            if (showUploadDate) {
                Text(
                    text = ". ${video.createdAt} ago",
                    style = MaterialTheme.typography.labelLarge.copy(color = Color.White.copy(0.6f))
                )
            }
        }
        Spacer(Modifier.height(5.dp))
        if(video.description.isNotEmpty()){
            Text(
                text = video.description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(0.85f)
            )
            Spacer(Modifier.height(10.dp))
        }
        val audioInfo = "Original sound - ${video.authorDetails.username}"
        Row(
            modifier = Modifier.rippleClickable {
                onAudioClick(video)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_music_note),
                contentDescription = null, tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = audioInfo,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .basicMarquee()
            )
        }
    }
}

@Composable
fun RotationAudioView(image: String) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = keyframes { durationMillis = 7000 })
    )
    Box(
        modifier = Modifier
            .graphicsLayer {
                rotationZ = angle
            }
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Gray20, Gray20, GrayLight, Gray20, Gray20,
                    )
                ), shape = CircleShape
            )
            .size(46.dp), contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape),
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}