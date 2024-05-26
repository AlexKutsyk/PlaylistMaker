package com.practicum.playlistmaker.library.favorites.data.db

import com.practicum.playlistmaker.library.favorites.data.db.convertors.TrackDbConvertor
import com.practicum.playlistmaker.library.favorites.data.db.entity.FavoriteTrackEntity
import com.practicum.playlistmaker.library.favorites.domain.db.FavoritesRepository
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritesRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val trackDbConvertor: TrackDbConvertor
) : FavoritesRepository {

    override suspend fun insertTrack(track: Track) {
        val tracksIdFavorites = appDataBase.trackDao().getIdFavoritesTrack()
        if (!tracksIdFavorites.contains(track.trackId)) {
            val trackEntity = trackDbConvertor.mapModelToEntity(track)
            appDataBase.trackDao().insertTrack(trackEntity)
        }
    }

    override suspend fun deleteTrack(track: Track) {
        val trackEntity = trackDbConvertor.mapModelToEntity(track)
        appDataBase.trackDao().deleteTrack(trackEntity)
    }

    override suspend fun getAllFavoritesTracks(): Flow<List<Track>> = flow {
        val favoritesTracks = appDataBase.trackDao().getFavoritesTracks()
        emit(convertListEntityToModel(favoritesTracks))
    }

    private fun convertListEntityToModel(listFavoriteTrackEntity: List<FavoriteTrackEntity>): List<Track> {
        return listFavoriteTrackEntity.map { trackEntity -> trackDbConvertor.mapEntityToModel(trackEntity) }
    }
}