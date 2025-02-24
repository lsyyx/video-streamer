package com.example.video_playback

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.domain.enteties.VideoItem

@SuppressLint("SourceLockedOrientationActivity")
@OptIn(UnstableApi::class)
@Composable
fun VideoPlaybackScreen(
    videoItem: VideoItem,
    viewModel: VideoPlaybackViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val isFullscreen by viewModel.isFullScreen.collectAsState()

    LaunchedEffect(videoItem.url) {
        viewModel.initPlayer(context, videoItem.url)
    }

    DisposableEffect(viewModel) {
        onDispose {
            viewModel.releasePlayer()
        }
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .zIndex(1f)
        ) {
            IconButton(
                onClick = {
                    onNavigateBack()
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(16f / 9f),
                factory = {
                    PlayerView(context).apply {
                        player = viewModel.exoPlayer

                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT

                        setFullscreenButtonClickListener {
                            viewModel.setFullscreen(true)
                            activity?.requestedOrientation =
                                SCREEN_ORIENTATION_LANDSCAPE
                        }

                        if (Build.VERSION.SDK_INT >= 29) {
                            transitionAlpha = 0.5f
                        }

                        useController = true
                        controllerAutoShow = true
                        setBackgroundColor(Color.Transparent.toArgb())
                    }
                })
        }
    }

    BackHandler {
        if (isFullscreen) {
            activity?.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
            viewModel.setFullscreen(false)
        } else {
            onNavigateBack()
        }
    }
}
