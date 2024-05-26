package com.practicum.playlistmaker.library.playlist.domain.impl

import com.practicum.playlistmaker.library.playlist.domain.PlaylistInteractor
import com.practicum.playlistmaker.library.playlist.domain.PlaylistRepository
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val playlistRepository: PlaylistRepository,
) : PlaylistInteractor {

    override suspend fun insertPlaylist(playlist: Playlist) {
        playlistRepository.insertPlaylist(playlist)
    }

    override suspend fun getListPlaylists(): Flow<MutableList<Playlist>> {
        return playlistRepository.getListPlayLists()
    }

    override suspend fun getPlaylist(playlistId: Int): Flow<Playlist> {
        return playlistRepository.getPlaylist(playlistId)
    }

    override suspend fun insertTrackToPlaylist(track: Track): Flow<Long> {
        return playlistRepository.insertTrackToPlaylist(track)
    }

    override suspend fun getTracksByPlaylistId(playlistId: Int): Flow<MutableList<Track>> {
        return playlistRepository.getTracksByPlaylistId(playlistId)
    }

    override suspend fun deleteSelectedTrackFromPlaylist(track: Track) {
        playlistRepository.deleteSelectedTrackFromPlaylist(track)
    }

    override suspend fun deletePlaylistById(playlistId: Int): Flow<Int> {
        return playlistRepository.deletePlaylistById(playlistId)
    }

    override suspend fun saveUpdatesPlaylist(playlist: Playlist) {
        playlistRepository.saveUpdatesPlaylist(playlist)
    }
}