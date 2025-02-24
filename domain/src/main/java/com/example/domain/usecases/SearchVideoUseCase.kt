package com.example.domain.usecases

import com.example.domain.repository.VideosRepository
import javax.inject.Inject

class SearchVideoUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend operator fun invoke(query: String) = repository.searchVideos(query)
}