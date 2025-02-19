package com.example.data.api

import com.example.data.constants.AppConstants
import com.example.data.enteties.YouTubeVideoItem
import com.example.data.enteties.YouTubeVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService {

    @GET("videos")
    suspend fun getPopularVideos(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("chart") chart: String = "mostPopular",
        @Query("regionCode") regionCode: String = "US",
        @Query("maxResults") maxResults: Int = 50,
        @Query("key") apiKey: String = AppConstants.API_KEY,
    ): Response<YouTubeVideoResponse>

    @GET("videos")
    fun getVideo(
        @Query("id") videoId: Long,
        @Query("part") part: String = "snippet,contentDetails",
        @Query("key") apiKey: String = AppConstants.API_KEY,
    ): Response<YouTubeVideoItem>
}