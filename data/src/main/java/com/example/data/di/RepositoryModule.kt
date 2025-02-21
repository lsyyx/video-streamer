package com.example.data.di

import com.example.core.di.IoDispatcher
import com.example.data.api.CoverrApiService
import com.example.data.repository.CoverrApiRepositoryImpl
import com.example.domain.repository.CoverrApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCoverrApiRepository(
        api: CoverrApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): CoverrApiRepository {
        return CoverrApiRepositoryImpl(api, ioDispatcher)
    }
}