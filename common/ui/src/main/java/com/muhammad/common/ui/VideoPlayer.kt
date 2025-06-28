package com.muhammad.common.ui

import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.muhammad.core.VideoCache
import com.muhammad.data.model.Video

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    video: Video,
    pagerState: PagerState,
    pageIndex: Int, modifier: Modifier,
    onSingleTap: (exoPlayer: ExoPlayer) -> Unit,
    onDoubleTap: (exoPlayer: ExoPlayer, offset: Offset) -> Unit,
    onVideoDispose: () -> Unit = {},
    onVideoGoBackground: () -> Unit = {},
) {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    var isThumbnailVisible by remember { mutableStateOf(true) }
    var isFirstFrameLoad by remember { mutableStateOf(false) }
    if (pagerState.settledPage == pageIndex) {
        val dataSourceFactory = remember { VideoCache.buildCacheDataSourceFactory(context) }
        val exoPlayer = remember(context) {
            ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(video.videoLink.toUri())
                val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem)
                setMediaSource(mediaSource)
                repeatMode = Player.REPEAT_MODE_ONE
                playWhenReady = true
                prepare()
                addListener(object : Player.Listener {
                    override fun onRenderedFirstFrame() {
                        isFirstFrameLoad = true
                        isThumbnailVisible = false
                    }
                })
            }
        }
        DisposableEffect(lifeCycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_STOP -> {
                        exoPlayer.pause()
                        onVideoGoBackground()
                    }

                    Lifecycle.Event.ON_START -> exoPlayer.play()
                    else -> Unit
                }
            }
            lifeCycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifeCycleOwner.lifecycle.removeObserver(observer)
            }
        }
        val playerView = remember {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
            }
        }
        DisposableEffect(
            AndroidView(factory = {
                playerView
            }, modifier = modifier.pointerInput(Unit) {
                detectTapGestures(onTap = { onSingleTap(exoPlayer) }, onDoubleTap = { offset ->
                    onDoubleTap(exoPlayer, offset)
                })
            }), effect = {
                onDispose {
                    exoPlayer.release()
                    isThumbnailVisible = true
                    onVideoDispose()
                }
            }
        )
    }
    if (isThumbnailVisible) {
        AsyncImage(
            model = video.thumbnail,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}