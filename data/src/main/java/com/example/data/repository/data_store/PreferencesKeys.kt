package com.example.data.repository.data_store

import androidx.datastore.preferences.core.longPreferencesKey

internal object PreferencesKeys {
    const val STORE_PREFERENCES_NAME = "app_preferences"
    val LAST_API_TOKEN = longPreferencesKey("last_api_call_time")
}