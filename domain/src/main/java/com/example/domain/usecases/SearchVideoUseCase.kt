package com.example.domain.usecases

import com.example.domain.repository.CoverrApiRepository
import javax.inject.Inject

class SearchVideoUseCase @Inject constructor(
    private val repository: CoverrApiRepository,
) {
    suspend operator fun invoke(query: String) = repository.getVideoList(query)
}