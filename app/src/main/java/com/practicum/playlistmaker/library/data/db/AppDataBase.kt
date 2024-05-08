package com.practicum.playlistmaker.library.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmaker.library.data.db.dao.TrackDao
import com.practicum.playlistmaker.library.data.db.entity.TrackEntity

@Database(entities = [TrackEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun trackDao(): TrackDao
}