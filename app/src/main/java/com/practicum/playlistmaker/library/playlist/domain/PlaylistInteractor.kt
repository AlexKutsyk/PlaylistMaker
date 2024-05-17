package com.practicum.playlistmaker.library.playlist.domain

import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import com.practicum.playlistmaker.library.playlist.domain.models.SelectedTrack
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {

    suspend fun insertPlaylist(playlist: Playlist)

    suspend fun getPlaylists(): Flow<MutableList<Playlist>>

    suspend fun insertTrackToPlaylist(selectedTrack: SelectedTrack): Flow<Long>

}