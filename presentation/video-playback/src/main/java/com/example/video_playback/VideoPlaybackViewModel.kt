package com.example.video_playback

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VideoPlaybackViewModel @Inject constructor() : ViewModel() {

    private var _exoPlayer: ExoPlayer? = null
    val exoPlayer: ExoPlayer
        get() = _exoPlayer ?: throw IllegalStateException("Player is not initialized")

    private val _isFullScreen = MutableStateFlow(false)
    val isFullScreen: StateFlow<Boolean> = _isFullScreen.asStateFlow()

    private var playbackPosition by mutableLongStateOf(0L)
    private var playWhenReady by mutableStateOf(true)
    var isBuffering by mutableStateOf(true)

    private var mediaItem: MediaItem? = null

    fun initPlayer(context: Context, videoUrl: String) {
        if (_exoPlayer != null) return

        mediaItem = MediaItem.fromUri(videoUrl)

        _exoPlayer = ExoPlayer.Builder(context).build().apply {
            mediaItem?.let { setMediaItem(it) }
            prepare()
            seekTo(playbackPosition)
            playWhenReady = this@VideoPlaybackViewModel.playWhenReady
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    isBuffering = playbackState == Player.STATE_BUFFERING
                }
            })
        }
    }

    fun releasePlayer() {
        _exoPlayer?.let {
            playbackPosition = it.currentPosition
            playWhenReady = it.playWhenReady
            it.removeListener(object : Player.Listener {})
            it.release()
        }
        _exoPlayer = null
    }

    fun setFullscreen(isFullscreen: Boolean) {
        _isFullScreen.value = isFullscreen
    }
}