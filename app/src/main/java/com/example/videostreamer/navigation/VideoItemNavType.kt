package com.example.videostreamer.navigation

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

inline fun <reified T : Parcelable> navTypeOf(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(
    isNullableAllowed = isNullableAllowed
) {
    override fun get(bundle: Bundle, key: String): T? {
        return when {
            SDK_INT >= Build.VERSION_CODES.TIRAMISU -> bundle.getParcelable(key, T::class.java)
            else -> @Suppress("DEPRECATION") bundle.getParcelable (key) as? T
        }
    }

    override fun parseValue(value: String): T {
       return json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: T): String {
        return json.encodeToString(value)
    }
}
