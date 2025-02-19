package com.example.domain.enteties

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

typealias VideoList = List<VideoItem>

@Serializable
@Parcelize
data class VideoItem(
    val id: String,
    val title: String,
    val image: String,
    val url: String,
    val duration: String,
) : Parcelable
