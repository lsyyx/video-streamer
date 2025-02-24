package com.example.videostreamer.di

import com.example.core.di.IoDispatcher
import com.example.data.di.RepositoryModule
import com.example.domain.repository.VideosRepository
import com.example.domain.usecases.GetVideoListUseCase
import com.example.domain.usecases.RefreshVideoUseCase
import com.example.domain.usecases.SearchVideoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object TestModule {

    @Provides
    fun provideVideoRepository(): VideosRepository {
        return FakeVideoRepositoryImpl()
    }

    @Provides
    fun provideGetVideoListUseCase(repository: VideosRepository): GetVideoListUseCase {
        return GetVideoListUseCase(repository)
    }

    @Provides
    fun provideSearchVideoUseCase(repository: VideosRepository): SearchVideoUseCase {
        return SearchVideoUseCase(repository)
    }

    @Provides
    fun provideRefreshVideoUseCase(repository: VideosRepository): RefreshVideoUseCase {
        return RefreshVideoUseCase(repository)
    }
}