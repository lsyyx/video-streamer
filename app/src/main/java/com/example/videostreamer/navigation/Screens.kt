package com.example.videostreamer.navigation

import com.example.domain.enteties.VideoItem
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object VideosRoute

@Serializable
data class VideoPlaybackRoute(
    val videoItem: VideoItem,
)