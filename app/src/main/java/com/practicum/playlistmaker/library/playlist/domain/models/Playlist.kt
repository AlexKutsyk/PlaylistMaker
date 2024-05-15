package com.practicum.playlistmaker.library.playlist.domain.models

import java.io.File

data class Playlist(
    var id: Int = 0,
    val namePlaylist: String,
    val descriptionPlaylist: String?,
    val pathImageCover: File?,
    var listTrackIds: MutableList<Int> = mutableListOf(),
    var amountTracks: Int = 0,
)
