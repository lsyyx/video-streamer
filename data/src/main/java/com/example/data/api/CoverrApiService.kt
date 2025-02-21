package com.example.data.api

import com.example.data.enteties.VideoListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoverrApiService {

    @GET("videos")
    suspend fun getVideoList(
        @Query("query") query: String? = null,
        @Query("page") page: Int = 0,
        @Query("page_size") pageSize: Int = 50,
        @Query("sort") sort: String = "popular",
        @Query("urls") urls: Boolean = true,
    ) : Response<VideoListDto>

//    @GET("videos")
//    suspend fun searchVideos(
//        @Query("page") page: Int = 0,
//        @Query("query") query: String,
//        @Query("page_size") pageSize: Int = 50,
//        @Query("sort") sort: String = "popular",
//        @Query("urls") urls: Boolean = true,
//    ) : Response<VideoListDto>
}