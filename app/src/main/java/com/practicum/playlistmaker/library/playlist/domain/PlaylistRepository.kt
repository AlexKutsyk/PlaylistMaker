package com.practicum.playlistmaker.library.playlist.domain

import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun insertPlaylist(playlist: Playlist)
    suspend fun insertTrackToPlaylist(track: Track): Flow<Long>
    suspend fun getListPlayLists(): Flow<MutableList<Playlist>>
    suspend fun getPlaylist(playlistId: Int): Flow<Playlist>
    suspend fun getTracksByPlaylistId(playlistId: Int): Flow<MutableList<Track>>
    suspend fun deleteSelectedTrackFromPlaylist(track: Track)
    suspend fun deletePlaylistById(playlistId: Int) : Flow<Int>
    suspend fun saveUpdatesPlaylist(playlist: Playlist)
}