package com.example.data.repository

import com.example.core.data_state.Loading
import com.example.core.data_state.UiState
import com.example.core.di.IoDispatcher
import com.example.data.api.CoverrApiService
import com.example.data.mapper.toVideoList
import com.example.domain.enteties.VideoItem
import com.example.domain.enteties.VideoList
import com.example.domain.repository.CoverrApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CoverrApiRepositoryImpl @Inject constructor(
    private val apiService: CoverrApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : CoverrApiRepository {

    override suspend fun getVideoList(query: String?): Flow<UiState<VideoList>> = flow {
        emit(handleState(
            execute = { apiService.getVideoList(query) },
            transform = { list -> list.toVideoList() }
        ))
    }.flowOn(ioDispatcher)

    override suspend fun getVideo(id: String): Flow<UiState<VideoItem>> {
        TODO("Not yet implemented")
    }
}