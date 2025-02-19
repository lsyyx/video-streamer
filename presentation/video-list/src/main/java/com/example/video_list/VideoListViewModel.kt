package com.example.video_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data_state.Loading
import com.example.core.data_state.UiState
import com.example.core.di.IoDispatcher
import com.example.domain.enteties.VideoList
import com.example.domain.usecases.GetVideoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val getVideoList: GetVideoListUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _videoList = MutableStateFlow<UiState<VideoList>>(Loading)
    val videoList: StateFlow<UiState<VideoList>> = _videoList.asStateFlow()

    fun loadVideos() = viewModelScope.launch(ioDispatcher) {
        getVideoList().collect { data ->
            _videoList.emit(data)
        }
    }
}