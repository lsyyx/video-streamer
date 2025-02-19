package com.example.data.di

import com.example.core.di.IoDispatcher
import com.example.data.api.YoutubeApiService
import com.example.data.repository.YoutubeApiRepositoryImpl
import com.example.domain.repository.YoutubeApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideYoutubeApiRepository(
        api: YoutubeApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): YoutubeApiRepository {
        return YoutubeApiRepositoryImpl(api, ioDispatcher)
    }
}