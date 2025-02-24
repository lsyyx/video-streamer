package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {

    @Query("SELECT * FROM videos")
    fun loadVideos(): List<VideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<VideoEntity>)

    @Query("DELETE FROM videos")
    fun deleteAll()
}