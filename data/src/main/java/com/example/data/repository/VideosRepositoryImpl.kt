package com.example.data.repository

import com.example.core.constants.AppConstants
import com.example.core.data_state.Success
import com.example.core.data_state.UiState
import com.example.core.di.IoDispatcher
import com.example.data.api.CoverrApiService
import com.example.data.local.VideoDao
import com.example.data.mapper.toVideoDbList
import com.example.data.mapper.toVideoList
import com.example.data.repository.data_store.DataStoreHelper
import com.example.domain.enteties.VideoList
import com.example.domain.repository.VideosRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VideosRepositoryImpl @Inject constructor(
    private val apiService: CoverrApiService,
    private val videosDao: VideoDao,
    private val dataStoreHelper: DataStoreHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : VideosRepository {

    override suspend fun getVideoList(): Flow<UiState<VideoList>> = flow {
        val lastApiCallTime = dataStoreHelper.getLastUpdateTime().firstOrNull() ?: 0L
        val cachedVideos = videosDao.loadVideos().toVideoList()

        if (cachedVideos.isNotEmpty() && lastApiCallTime != 0L &&
            (System.currentTimeMillis() - lastApiCallTime) <= AppConstants.REFRESH_INTERVAL_MS
        ) {
            emit(Success(cachedVideos))
        } else {
            val result = getVideoListFromApi()
            emit(result)
            if (result is Success) {
                cacheVideos(result.data)
                dataStoreHelper.saveTime(System.currentTimeMillis())
            }
        }
    }.flowOn(ioDispatcher)

    override suspend fun searchVideos(query: String): Flow<UiState<VideoList>> = flow {
        emit(getVideoListFromApi(query))
    }.flowOn(ioDispatcher)

    override suspend fun refreshVideoList(query: String?): Flow<UiState<VideoList>> = flow {
        val result = getVideoListFromApi().also { emit(it) }
        if (result is Success) {
            cacheVideos(result.data)
        }
    }.flowOn(ioDispatcher)

    private suspend fun getVideoListFromApi(query: String? = null): UiState<VideoList> {
        return handleState(
            execute = { apiService.getVideoList(query) },
            transform = { list -> list.toVideoList() }
        )
    }

    override suspend fun getCachedVideoList(): VideoList {
        return videosDao.loadVideos().toVideoList()
    }

    override suspend fun cacheVideos(videos: VideoList) {
        videosDao.deleteAll()
        videosDao.insertAll(videos.toVideoDbList())
    }
}