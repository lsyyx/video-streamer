package com.example.domain.repository

import com.example.core.data_state.UiState
import com.example.domain.enteties.VideoItem
import com.example.domain.enteties.VideoList
import kotlinx.coroutines.flow.Flow

interface YoutubeApiRepository {

    suspend fun getPopularVideo() : Flow<UiState<VideoList>>
    suspend fun getVideo(id: Long) : Flow<UiState<VideoItem>>
}