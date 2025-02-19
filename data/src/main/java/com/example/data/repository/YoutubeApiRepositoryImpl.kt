package com.example.data.repository

import com.example.core.data_state.UiState
import com.example.core.data_state.Loading
import com.example.core.di.IoDispatcher
import com.example.data.api.YoutubeApiService
import com.example.data.mapper.toVideo
import com.example.data.mapper.toVideoList
import com.example.domain.enteties.VideoItem
import com.example.domain.enteties.VideoList
import com.example.domain.repository.YoutubeApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class YoutubeApiRepositoryImpl @Inject constructor(
    private val apiService: YoutubeApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : YoutubeApiRepository {

    override suspend fun getPopularVideo(): Flow<UiState<VideoList>> = flow {
        emit(Loading)
        emit(handleState(
            execute = { apiService.getPopularVideos() },
            transform = { list -> list.toVideoList() }
        ))
    }.flowOn(ioDispatcher)

    override suspend fun getVideo(id: Long): Flow<UiState<VideoItem>> = flow {
        emit(Loading)
        emit(handleState(
            execute = { apiService.getVideo(id) },
            transform = { list -> list.toVideo() }
        ))
    }.flowOn(ioDispatcher)
}