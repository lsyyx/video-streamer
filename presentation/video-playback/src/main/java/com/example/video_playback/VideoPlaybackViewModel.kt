package com.example.video_playback

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.core.data_state.Loading
import com.example.core.data_state.UiState
import com.example.core.di.IoDispatcher
import com.example.domain.enteties.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class VideoPlaybackViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _videoItem = MutableStateFlow<UiState<VideoItem>>(Loading)
    val videoItem: StateFlow<UiState<VideoItem>> = _videoItem

    var playbackPosition by mutableLongStateOf(0L)
    var playWhenReady by mutableStateOf(true)
    var isBuffering by mutableStateOf(true)

}