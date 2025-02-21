package com.example.data.enteties

data class VideoDto(
    val id: String,
    val title: String,
    val thumbnail: String,
    val duration: Double,
    val urls: UrlsDto,
)
