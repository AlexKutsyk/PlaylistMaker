package com.practicum.playlistmaker.library.playlist.data.convertors

import com.google.gson.Gson
import com.practicum.playlistmaker.library.playlist.data.entity.PlaylistEntity
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import java.io.File

class PlaylistDBConvertor(private val gson: Gson) {

    fun mapModelToEntity(playlist: Playlist): PlaylistEntity = PlaylistEntity(
        playlist.id,
        playlist.namePlaylist,
        playlist.descriptionPlaylist,
        gson.toJson(playlist.pathImageCover),
        gson.toJson(playlist.listTrackIds),
        playlist.listTrackIds.size
    )

    fun mapEntityToModel(playlistEntity: PlaylistEntity): Playlist = Playlist(
        playlistEntity.id,
        playlistEntity.namePlaylist,
        playlistEntity.descriptionPlaylist,
        gson.fromJson(playlistEntity.pathImageCover, File::class.java),
        gson.fromJson(playlistEntity.listTrackIds, Array<Int>::class.java).toMutableList(),
        playlistEntity.amountTracks
    )
}