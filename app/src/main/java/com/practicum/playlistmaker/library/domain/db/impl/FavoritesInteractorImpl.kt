package com.practicum.playlistmaker.library.domain.db.impl

import com.practicum.playlistmaker.library.domain.db.FavoritesInteractor
import com.practicum.playlistmaker.library.domain.db.FavoritesRepository
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavoritesInteractorImpl(
    private val favoritesRepository: FavoritesRepository
): FavoritesInteractor {
    override suspend fun insertTrack(track: Track) {
        return favoritesRepository.insertTrack(track)
    }

    override suspend fun deleteTrack(track: Track){
        favoritesRepository.deleteTrack(track)
    }

    override suspend fun getAllFavoritesTracks(): Flow<List<Track>> {
        return favoritesRepository.getAllFavoritesTracks()
    }
}