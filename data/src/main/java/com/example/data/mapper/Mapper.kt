package com.example.data.mapper

import com.example.data.enteties.VideoDto
import com.example.data.enteties.VideoListDto
import com.example.data.local.VideoEntity
import com.example.domain.enteties.VideoItem
import com.example.domain.enteties.VideoList
import java.util.Locale


fun VideoDto.toVideo() = VideoItem(
    id = id,
    title = title,
    url = urls.mp4,
    image = thumbnail,
    duration = duration.toFormattedDuration(),
)

fun VideoListDto.toVideoList() = hits.map { it.toVideo() }

fun VideoEntity.toVideo() = VideoItem(
    id = videoId,
    title = title,
    url = url,
    image = image,
    duration = duration,
)

fun VideoItem.toVideoEntity() = VideoEntity(
    videoId = id,
    title = title,
    url = url,
    image = image,
    duration = duration,
)

fun List<VideoEntity>.toVideoList() = this.map { it.toVideo() }
fun VideoList.toVideoDbList() = this.map { it.toVideoEntity() }

fun Double.toFormattedDuration(): String {
    val totalSeconds = this.toInt()
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    val format = "%02d:%02d"
    return String.format(Locale.ROOT, format, minutes, seconds)
}

