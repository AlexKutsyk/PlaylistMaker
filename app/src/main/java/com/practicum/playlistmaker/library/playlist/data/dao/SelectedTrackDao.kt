package com.practicum.playlistmaker.library.playlist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.library.playlist.data.entity.SelectedTrackEntity

@Dao
interface SelectedTrackDao {

    @Insert(
        entity = SelectedTrackEntity::class,
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun insertTrackToPlaylist(selectedTrackEntity: SelectedTrackEntity): Long

    @Query("SELECT trackId FROM selected_track_table WHERE playlistId LIKE :playlistId")  //Repo
    suspend fun getListTrackIdOfPlaylistByPlaylistId(playlistId: Int): MutableList<Int>

    @Query("SELECT trackTimeMillis FROM selected_track_table WHERE playlistId LIKE :playlistId")  //Repo
    suspend fun getListTrackTimeOfPlaylistById(playlistId: Int): MutableList<Int>

    @Query("SELECT * FROM selected_track_table WHERE playlistId LIKE :playlistId")
    suspend fun getTracksByPlaylistId(playlistId: Int): MutableList<SelectedTrackEntity>

    @Delete(entity = SelectedTrackEntity::class)
    suspend fun deleteSelectedTrackFromPlaylist(trackEntity: SelectedTrackEntity)
}