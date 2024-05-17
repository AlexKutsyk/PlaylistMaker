package com.practicum.playlistmaker.library.playlist.data.convertors

import com.practicum.playlistmaker.library.playlist.data.entity.SelectedTrackEntity
import com.practicum.playlistmaker.library.playlist.domain.models.SelectedTrack

class SelectedTrackDBConvertor {

    fun mapModelToEntity(selectedTrack: SelectedTrack): SelectedTrackEntity = SelectedTrackEntity(
        selectedTrack.id,
        selectedTrack.playlistId,
        selectedTrack.trackName,
        selectedTrack.artistName,
        selectedTrack.trackTimeMillis,
        selectedTrack.artworkUrl100,
        selectedTrack.trackId,
        selectedTrack.collectionName,
        selectedTrack.releaseDate,
        selectedTrack.primaryGenreName,
        selectedTrack.country,
        selectedTrack.previewUrl,
        selectedTrack.isFavorite,
    )

    fun mapEntityToModel(selectedTrackEntity: SelectedTrackEntity): SelectedTrack = SelectedTrack(
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