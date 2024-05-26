package com.practicum.playlistmaker.library.playlist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.practicum.playlistmaker.library.playlist.data.entity.PlaylistEntity

@Dao
interface PlaylistDao {

    @Insert(
        entity = PlaylistEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertPlaylist(playlistEntity: PlaylistEntity)

    @Update(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE) //Repo
    suspend fun updatePlaylist(playlistEntity: PlaylistEntity)

    @Query("SELECT * FROM playlist_table")
    suspend fun getPlaylists(): MutableList<PlaylistEntity>

    @Query("SELECT * FROM playlist_table WHERE id = :playlistId") //Repo
    suspend fun getPlaylistById(playlistId: Int): PlaylistEntity

    @Delete(entity = PlaylistEntity::class)
    suspend fun deletePlaylist(playlistEntity: PlaylistEntity): Int
}