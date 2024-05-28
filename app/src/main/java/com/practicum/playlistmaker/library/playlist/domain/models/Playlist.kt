package com.practicum.playlistmaker.library.playlist.domain.models

import android.net.Uri

data class Playlist(
    var id: Int = 0,
    var namePlaylist: String,
    var descriptionPlaylist: String?,
    var uriImageStorage: Uri?,
    var listTrackIds: MutableList<Int> = mutableListOf(),
    var amountTracks: Int = 0,
    var totalPlaylistTime: Int = 0,
    var trackSpelling: String = "треков",
    var minutesSpelling: String = "минут",
)
