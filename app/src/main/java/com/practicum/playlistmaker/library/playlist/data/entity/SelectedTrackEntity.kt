package com.practicum.playlistmaker.library.playlist.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_track_table")
//    foreignKeys = [ForeignKey(
//        entity = PlaylistEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("playlistId"),
//        onDelete = ForeignKey.CASCADE
//    )]

data class SelectedTrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
//    @ColumnInfo(name = "playlist_id")
    val playlistId: Int,
    val trackName: String?,
    val artistName: String?,
    val trackTimeMillis: Int?,
    val artworkUrl100: String?,
    @ColumnInfo(name = "track_id")
    val trackId: Int,
    val collectionName: String?,
    val releaseDate: String?,
    val primaryGenreName: String?,
    val country: String?,
    val previewUrl: String?,
    var isFavorite: Boolean,
)
