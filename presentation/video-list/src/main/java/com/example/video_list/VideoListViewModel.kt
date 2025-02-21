package com.example.video_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data_state.Loading
import com.example.core.data_state.UiState
import com.example.core.di.IoDispatcher
import com.example.domain.enteties.VideoList
import com.example.domain.usecases.GetVideoListUseCase
import com.example.domain.usecases.SearchVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val getVideoListUseCase: GetVideoListUseCase,
    private val searchVideoUseCase: SearchVideoUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _videoList = MutableStateFlow<UiState<VideoList>>(Loading)
    val videoList: StateFlow<UiState<VideoList>> = _videoList.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _query = MutableStateFlow("")

    fun loadVideos() = viewModelScope.launch(ioDispatcher) {
        _videoList.emit(Loading)
        getVideoListUseCase().collect { data ->
            _videoList.emit(data)
        }
        _query.emit("")
    }

    fun searchVideo(query: String) = viewModelScope.launch(ioDispatcher) {
        _query.emit(query)
        _videoList.emit(Loading)
        searchVideoUseCase(query).collect { data ->
            _videoList.emit(data)
        }
    }

    fun refresh() = viewModelScope.launch(ioDispatcher) {
        _isRefreshing.emit(true)
        if (_query.value.isNotBlank()) {
            searchVideoUseCase(_query.value).collect { data ->
                _videoList.emit(data)
                _isRefreshing.emit(false)
            }
        } else {
            getVideoListUseCase().collect { data ->
                _videoList.emit(data)
            }
        }
        _isRefreshing.emit(false)
    }
}