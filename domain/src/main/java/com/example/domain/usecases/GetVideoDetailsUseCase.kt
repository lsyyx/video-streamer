package com.example.domain.usecases

import com.example.domain.repository.CoverrApiRepository
import javax.inject.Inject

class GetVideoDetailsUseCase @Inject constructor(
    private val repository: CoverrApiRepository,
) {
    suspend operator fun invoke(id: String) = repository.getVideo(id)
}