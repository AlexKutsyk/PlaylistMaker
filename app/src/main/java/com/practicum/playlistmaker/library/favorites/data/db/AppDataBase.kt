package com.practicum.playlistmaker.library.favorites.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmaker.library.favorites.data.db.dao.TrackDao
import com.practicum.playlistmaker.library.favorites.data.db.entity.FavoriteTrackEntity
import com.practicum.playlistmaker.library.playlist.data.dao.PlaylistDao
import com.practicum.playlistmaker.library.playlist.data.dao.SelectedTrackDao
import com.practicum.playlistmaker.library.playlist.data.entity.PlaylistEntity
import com.practicum.playlistmaker.library.playlist.data.entity.SelectedTrackEntity

@Database(
    entities = [
        FavoriteTrackEntity::class,
        PlaylistEntity::class,
        SelectedTrackEntity::class,
    ],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun selectedTrackDao(): SelectedTrackDao
}