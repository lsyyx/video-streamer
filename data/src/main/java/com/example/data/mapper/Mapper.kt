package com.example.data.mapper

import com.example.data.enteties.YouTubeVideoItem
import com.example.data.enteties.YouTubeVideoResponse
import com.example.domain.enteties.VideoItem
import kotlin.time.Duration

fun YouTubeVideoItem.toVideo() = VideoItem(
    id = id,
    title = snippet.title,
    url = id.toVideoUrl(),
    image = snippet.thumbnails.medium.url,
    duration = contentDetails.duration.toFormattedDuration(),
)

fun YouTubeVideoResponse.toVideoList() = items.map { it.toVideo() }

fun String.toVideoUrl() = "https://www.youtube.com/watch?v=$this"

fun String.toFormattedDuration() = Duration.parseOrNull(this).toString()