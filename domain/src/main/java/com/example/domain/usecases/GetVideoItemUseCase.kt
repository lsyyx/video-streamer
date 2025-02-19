package com.example.domain.usecases

import com.example.domain.repository.YoutubeApiRepository
import javax.inject.Inject

class GetVideoItemUseCase @Inject constructor(
    private val repository: YoutubeApiRepository,
) {
    suspend operator fun invoke(id: Long) = repository.getVideo(id)
}