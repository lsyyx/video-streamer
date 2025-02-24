package com.example.data.repository.data_store

import kotlinx.coroutines.flow.Flow

interface DataStoreHelper {
    suspend fun saveTime(time: Long)
    fun getLastUpdateTime(): Flow<Long>
}