package com.example.video_list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data_state.Loading
import com.example.core.data_state.UiState
import com.example.core.di.IoDispatcher
import com.example.domain.enteties.VideoList
import com.example.domain.usecases.GetVideoListUseCase
import com.example.domain.usecases.RefreshVideoUseCase
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
    private val refreshVideoUseCase: RefreshVideoUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _videoList = MutableStateFlow<UiState<VideoList>>(Loading)
    val videoList: StateFlow<UiState<VideoList>> = _videoList.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val listState = mutableStateOf(LazyListState())

    @OptIn(ExperimentalMaterial3Api::class)
    val pullToRefreshState = mutableStateOf(PullToRefreshState())

    init {
        loadVideos()
    }

    private fun loadVideos() = viewModelScope.launch(ioDispatcher) {
        _videoList.emit(Loading)
        getVideoListUseCase().collect { data ->
            _videoList.emit(data)
        }
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
        refreshVideoUseCase(query.value).collect { data ->
            _videoList.emit(data)
        }
        _isRefreshing.emit(false)
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    fun clearQuery() {
        _query.value = ""
        loadVideos()
    }
}