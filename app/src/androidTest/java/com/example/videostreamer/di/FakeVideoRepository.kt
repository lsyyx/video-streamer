package com.example.videostreamer.di

import com.example.core.data_state.Loading
import com.example.core.data_state.Success
import com.example.core.data_state.UiState
import com.example.domain.enteties.VideoItem
import com.example.domain.enteties.VideoList
import com.example.domain.repository.VideosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeVideoRepositoryImpl : VideosRepository {

    private val videos = listOf(
        VideoItem(
            id = "0",
            title = "Video Test 1",
            image = "image1",
            url = "https://cdn.coverr.co/videos/coverr-flickering-christmas-lights-5452/1080p.mp4",
            duration = "00:13"
        ), VideoItem(
            id = "1",
            title = "Video Test 2",
            image = "image2",
            url = "https://cdn.coverr.co/videos/coverr-year-end-sale-sign-9942/1080p.mp4",
            duration = "00:12"
        )
    )

    override suspend fun getVideoList(): Flow<UiState<VideoList>> = flow {
        emit(Loading)
        delay(2000)
        emit(Success(data = videos))
    }.flowOn(Dispatchers.IO)

    override suspend fun searchVideos(query: String): Flow<UiState<VideoList>> = flow {
        emit(Loading)
        delay(2000)
        emit(Success(data = videos))
    }.flowOn(Dispatchers.IO)

    override suspend fun refreshVideoList(query: String?): Flow<UiState<VideoList>> = getVideoList()

    override suspend fun getCachedVideoList(): VideoList = videos

    override suspend fun cacheVideos(videos: VideoList) {}
}