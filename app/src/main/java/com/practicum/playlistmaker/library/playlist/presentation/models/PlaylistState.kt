package com.practicum.playlistmaker.library.playlist.presentation.models

import com.practicum.playlistmaker.library.playlist.domain.models.Playlist

sealed class PlaylistState (
    val playlists: MutableList<Playlist>
){
    class Content(playlists: MutableList<Playlist>): PlaylistState(playlists)
    class Empty: PlaylistState(emptyList<Playlist>().toMutableList())
}