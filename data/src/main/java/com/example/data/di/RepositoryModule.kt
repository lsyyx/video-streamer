package com.example.data.di

import android.content.Context
import com.example.core.di.IoDispatcher
import com.example.data.api.CoverrApiService
import com.example.data.local.VideoDao
import com.example.data.repository.VideosRepositoryImpl
import com.example.data.repository.data_store.DataStoreHelper
import com.example.data.repository.data_store.DataStoreHelperImpl
import com.example.domain.repository.VideosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreHelper(
        @ApplicationContext context: Context
    ): DataStoreHelper {
        return DataStoreHelperImpl(context)
    }

    @Provides
    @Singleton
    fun provideCoverrApiRepository(
        api: CoverrApiService,
        videoDao: VideoDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        dataStoreHelper: DataStoreHelper,
    ): VideosRepository {
        return VideosRepositoryImpl(
            apiService = api,
            videosDao = videoDao,
            dataStoreHelper = dataStoreHelper,
            ioDispatcher = ioDispatcher
        )
    }
}