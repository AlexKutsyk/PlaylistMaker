package com.practicum.playlistmaker.library.playlist.data.convertors

import com.practicum.playlistmaker.library.playlist.data.entity.SelectedTrackEntity
import com.practicum.playlistmaker.search.domain.models.Track

class SelectedTrackDBConvertor {

    fun mapModelToEntity(track: Track): SelectedTrackEntity = SelectedTrackEntity(
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

    fun mapEntityToModel(selectedTrackEntity: SelectedTrackEntity): Track = Track(
        selectedTrackEntity.id,
        selectedTrackEntity.playlistId,
        selectedTrackEntity.trackName,
        selectedTrackEntity.artistName,
        selectedTrackEntity.trackTimeMillis,
        selectedTrackEntity.artworkUrl100,
        selectedTrackEntity.trackId,
        selectedTrackEntity.collectionName,
        selectedTrackEntity.releaseDate,
        selectedTrackEntity.primaryGenreName,
        selectedTrackEntity.country,
        selectedTrackEntity.previewUrl,
        selectedTrackEntity.isFavorite
    )
}