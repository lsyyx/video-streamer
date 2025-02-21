package com.example.video_playback

import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.common.AppLoader
import com.example.domain.enteties.VideoItem

@OptIn(UnstableApi::class)
@Composable
fun VideoPlaybackScreen(
    videoItem: VideoItem,
    isFullScreen: Boolean,
//    onFullScreenToggle: (Boolean) -> Unit,
    viewModel: VideoPlaybackViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(videoItem.url)
            setMediaItem(mediaItem)
            prepare()
            seekTo(viewModel.playbackPosition)
            playWhenReady = viewModel.playWhenReady
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    viewModel.isBuffering = playbackState == Player.STATE_BUFFERING
                }
            })
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.playbackPosition = exoPlayer.currentPosition
            viewModel.playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
    }
    BackHandler { onNavigateBack() }
    val isBuffering = viewModel.isBuffering

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        val videoModifier = if (isFullScreen) {
            Modifier
                .fillMaxSize()
                .aspectRatio(16f / 9f)
        } else {
            Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        }
        Box(
            modifier = videoModifier
                .border(width = 0.5.dp, color = Color.White, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black)
                .zIndex(1f)
        ) {
            AndroidView(
                modifier = videoModifier,
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = true
                        controllerAutoShow = true
                    }
                }
            )
        }
    }

    if (isBuffering) {
        AppLoader()
    }
}