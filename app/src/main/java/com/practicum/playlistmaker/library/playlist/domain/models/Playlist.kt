package com.practicum.playlistmaker.library.playlist.domain.models

import android.net.Uri

data class Playlist(
    var id: Int = 0,
    val namePlaylist: String,
    val descriptionPlaylist: String?,
    var uriImageStorage: Uri?,
    var listTrackIds: MutableList<Int> = mutableListOf(),
    var amountTracks: Int = 0,
)
