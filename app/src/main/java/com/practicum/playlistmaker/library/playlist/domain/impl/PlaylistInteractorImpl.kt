package com.practicum.playlistmaker.library.playlist.domain.impl

import com.practicum.playlistmaker.library.playlist.domain.PlaylistInteractor
import com.practicum.playlistmaker.library.playlist.domain.PlaylistRepository
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import com.practicum.playlistmaker.library.playlist.domain.models.SelectedTrack
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val playlistRepository: PlaylistRepository,
) : PlaylistInteractor {

    override suspend fun insertPlaylist(playlist: Playlist) {
        playlistRepository.insertPlaylist(playlist)
    }

    override suspend fun getPlaylists(): Flow<MutableList<Playlist>> {
        return playlistRepository.getPlayLists()
    }

    override suspend fun insertTrackToPlaylist(selectedTrack: SelectedTrack): Flow<Long> {
        return playlistRepository.insertTrackToPlaylist(selectedTrack)
    }

}