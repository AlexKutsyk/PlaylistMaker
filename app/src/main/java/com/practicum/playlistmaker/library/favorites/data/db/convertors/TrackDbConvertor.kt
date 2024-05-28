package com.practicum.playlistmaker.library.favorites.data.db.convertors

import com.practicum.playlistmaker.library.favorites.data.db.entity.FavoriteTrackEntity
import com.practicum.playlistmaker.search.domain.models.Track

class TrackDbConvertor {

    fun mapModelToEntity(track: Track): FavoriteTrackEntity = FavoriteTrackEntity(
            track.id,
            track.playlistId,
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl100,
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            true
        )

    fun mapEntityToModel(track: FavoriteTrackEntity): Track = Track(
            track.id,
            track.playlistId,
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl100,
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            track.isFavorite,
        )
}