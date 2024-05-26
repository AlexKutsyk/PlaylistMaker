package com.practicum.playlistmaker.library.playlist.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_track_table")

data class SelectedTrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val playlistId: Int,
    val trackName: String?,
    val artistName: String?,
    val trackTimeMillis: Int?,
    val artworkUrl100: String?,
    val trackId: Int,
    val collectionName: String?,
    val releaseDate: String?,
    val primaryGenreName: String?,
    val country: String?,
    val previewUrl: String?,
    var isFavorite: Boolean,
)
