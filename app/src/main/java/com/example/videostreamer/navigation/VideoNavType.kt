@file:Suppress("DEPRECATION")

package com.example.videostreamer.navigation

import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.navigation.NavType
import com.example.domain.enteties.VideoItem
import kotlinx.serialization.json.Json

object VideoNavType {

    val VideoType = object : NavType<VideoItem>(
        isNullableAllowed = false
    ) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): VideoItem? {
            return when {
                SDK_INT >= Build.VERSION_CODES.TIRAMISU -> bundle.getParcelable(
                    key,
                    VideoItem::class.java
                )

                else -> bundle.getParcelable<VideoItem>(key)
            }
        }

        override fun parseValue(value: String): VideoItem {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: VideoItem): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: VideoItem,
        ) {
            bundle.putParcelable(key, value)
        }
    }
}