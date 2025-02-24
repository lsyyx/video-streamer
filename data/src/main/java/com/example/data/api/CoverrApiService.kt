package com.example.data.api

import com.example.core.constants.AppConstants
import com.example.data.enteties.VideoListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoverrApiService {

    @GET("videos")
    suspend fun getVideoList(
        @Query("query") query: String? = null,
        @Query("page") page: Int = 0,
        @Query("page_size") pageSize: Int = AppConstants.MAX_API_RESULTS,
        @Query("sort") sort: String = "popular",
        @Query("urls") urls: Boolean = true,
    ): Response<VideoListDto>
}