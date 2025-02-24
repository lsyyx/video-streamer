package com.example.domain.repository

import com.example.core.data_state.UiState
import com.example.domain.enteties.VideoList
import kotlinx.coroutines.flow.Flow

interface VideosRepository {

    suspend fun getVideoList(): Flow<UiState<VideoList>>

    suspend fun searchVideos(query: String): Flow<UiState<VideoList>>

    suspend fun refreshVideoList(query: String? = null): Flow<UiState<VideoList>>

    suspend fun getCachedVideoList(): VideoList

    suspend fun cacheVideos(videos: VideoList)
}