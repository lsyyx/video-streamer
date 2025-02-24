package com.example.data.repository.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreHelperImpl @Inject constructor(
    private val context: Context
) : DataStoreHelper {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PreferencesKeys.STORE_PREFERENCES_NAME)


    override suspend fun saveTime(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_API_TOKEN] = time
        }
    }

    override fun getLastUpdateTime(): Flow<Long> =
        context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.LAST_API_TOKEN] ?: 0L
        }
}