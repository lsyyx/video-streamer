package com.example.domain.usecases

import com.example.domain.repository.VideosRepository
import javax.inject.Inject

class RefreshVideoUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend operator fun invoke(query: String) = repository.refreshVideoList(query)
}