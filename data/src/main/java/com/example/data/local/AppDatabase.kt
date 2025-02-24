package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [VideoEntity::class],
    exportSchema = false,
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videosDao() : VideoDao
}