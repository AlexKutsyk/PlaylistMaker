package com.practicum.playlistmaker.library.favorites.domain.db

import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun insertTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    suspend fun getAllFavoritesTracks(): Flow<List<Track>>
}