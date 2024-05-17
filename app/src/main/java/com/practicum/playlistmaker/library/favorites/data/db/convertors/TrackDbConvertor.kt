package com.practicum.playlistmaker.library.favorites.data.db.convertors

import com.practicum.playlistmaker.library.favorites.data.db.entity.TrackEntity
import com.practicum.playlistmaker.search.domain.models.Track

class TrackDbConvertor {

    fun mapModelToEntity(track: Track): TrackEntity = TrackEntity(
            track.id,
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

    fun mapEntityToModel(track: TrackEntity): Track = Track(
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
            track.id,
            track.isFavorite,
        )
}