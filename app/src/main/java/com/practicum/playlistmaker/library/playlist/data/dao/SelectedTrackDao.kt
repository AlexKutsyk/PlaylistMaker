package com.practicum.playlistmaker.library.playlist.data.dao

import androidx.room.Dao
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

    @Query("SELECT track_id FROM selected_track_table WHERE playlistId LIKE :playlistId")  //Repo
    suspend fun getTrackIdOfPlaylistByPlaylistId(playlistId: Int): MutableList<Int>

}