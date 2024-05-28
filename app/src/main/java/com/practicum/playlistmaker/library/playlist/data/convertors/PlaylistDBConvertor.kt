package com.practicum.playlistmaker.library.playlist.data.convertors

import androidx.core.net.toUri
import com.google.gson.Gson
import com.practicum.playlistmaker.library.playlist.data.entity.PlaylistEntity
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist

class PlaylistDBConvertor(private val gson: Gson) {

    fun mapModelToEntity(playlist: Playlist): PlaylistEntity = PlaylistEntity(
        id = playlist.id,
        namePlaylist = playlist.namePlaylist,
        descriptionPlaylist = playlist.descriptionPlaylist,
        uriImageStorage = playlist.uriImageStorage.toString(),
        listTrackIds = gson.toJson(playlist.listTrackIds),
        amountTracks = playlist.listTrackIds.size,
        playlist.totalPlaylistTime,
        playlist.trackSpelling,
        playlist.minutesSpelling
    )

    fun mapEntityToModel(playlistEntity: PlaylistEntity): Playlist = Playlist(
        id = playlistEntity.id,
        namePlaylist = playlistEntity.namePlaylist,
        descriptionPlaylist = playlistEntity.descriptionPlaylist,
        uriImageStorage = playlistEntity.uriImageStorage?.toUri(),
        listTrackIds = gson.fromJson(playlistEntity.listTrackIds, Array<Int>::class.java).toMutableList(),
        amountTracks = playlistEntity.amountTracks,
        playlistEntity.totalPlaylistTime,
        playlistEntity.trackSpelling,
        playlistEntity.minutesSpelling
    )
}