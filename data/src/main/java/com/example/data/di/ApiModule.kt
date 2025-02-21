package com.example.data.di

import com.example.data.api.CoverrApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideCoverrApi(retrofit: Retrofit): CoverrApiService {
        return retrofit.create(CoverrApiService::class.java)
    }
}