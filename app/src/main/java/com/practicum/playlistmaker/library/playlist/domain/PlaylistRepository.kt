package com.practicum.playlistmaker.library.playlist.domain

import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import com.practicum.playlistmaker.library.playlist.domain.models.SelectedTrack
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun insertPlaylist(playlist: Playlist)
    suspend fun getPlayLists(): Flow<MutableList<Playlist>>
    suspend fun insertTrackToPlaylist(selectedTrack: SelectedTrack): Flow<Long>
}