package com.example.domain.usecases

import com.example.domain.repository.VideosRepository
import javax.inject.Inject

class GetVideoListUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend operator fun invoke() = repository.getVideoList()
}