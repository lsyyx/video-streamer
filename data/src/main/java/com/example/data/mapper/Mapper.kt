package com.example.data.mapper

import com.example.data.enteties.VideoDto
import com.example.data.enteties.VideoListDto
import com.example.domain.enteties.VideoItem
import java.util.Locale


fun VideoDto.toVideo() = VideoItem(
    id = id,
    title = title,
    url = urls.mp4,
    image = thumbnail,
    duration = duration.toFormattedDuration(),
)

fun VideoListDto.toVideoList() = hits.map { it.toVideo() }

fun Double.toFormattedDuration(): String {
    val totalSeconds = this.toInt() //
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    val format = "%02d:%02d" // coverr's api provides only short video
    return String.format(Locale.ROOT, format, minutes, seconds)
}

