package com.practicum.playlistmaker.library.playlist.presentation.models

import com.practicum.playlistmaker.search.domain.models.Track

sealed class TracksOfPlaylistState {
    data object EmptyPlaylist: TracksOfPlaylistState()
    class ContentPlaylist(val listTracksOfPlaylist: MutableList<Track>): TracksOfPlaylistState()
}