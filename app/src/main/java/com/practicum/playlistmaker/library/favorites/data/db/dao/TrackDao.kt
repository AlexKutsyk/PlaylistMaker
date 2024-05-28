package com.practicum.playlistmaker.library.favorites.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.library.favorites.data.db.entity.FavoriteTrackEntity

@Dao
interface TrackDao {

    @Insert(
        entity = FavoriteTrackEntity::class,
        onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: FavoriteTrackEntity)

    @Delete(entity = FavoriteTrackEntity::class)
    suspend fun deleteTrack(track: FavoriteTrackEntity)

    @Query("SELECT * FROM favorite_table")
    suspend fun getFavoritesTracks(): List<FavoriteTrackEntity>

    @Query("SELECT trackId FROM favorite_table")
    suspend fun getIdFavoritesTrack(): List<Int>
}