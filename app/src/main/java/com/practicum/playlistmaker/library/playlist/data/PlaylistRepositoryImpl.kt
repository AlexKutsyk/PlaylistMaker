package com.practicum.playlistmaker.library.playlist.data

import com.practicum.playlistmaker.library.favorites.data.db.AppDataBase
import com.practicum.playlistmaker.library.playlist.data.convertors.PlaylistDBConvertor
import com.practicum.playlistmaker.library.playlist.data.convertors.SelectedTrackDBConvertor
import com.practicum.playlistmaker.library.playlist.data.entity.PlaylistEntity
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import com.practicum.playlistmaker.library.playlist.domain.PlaylistRepository
import com.practicum.playlistmaker.library.playlist.domain.models.SelectedTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val playlistDBConvertor: PlaylistDBConvertor,
    private val selectedTrackDBConvertor: SelectedTrackDBConvertor
) : PlaylistRepository {

    override suspend fun insertPlaylist(playlist: Playlist) {
        val playlistEntity = playlistDBConvertor.mapModelToEntity(playlist)
        appDataBase.playlistDao().insertPlaylist(playlistEntity)
    }

    private suspend fun updatePlaylist (selectedTrack: SelectedTrack) {

        val playlistEntityIn = appDataBase.playlistDao().getPlaylistById(selectedTrack.playlistId)
        val playlist = playlistDBConvertor.mapEntityToModel(playlistEntityIn)

        val listTrackIds = appDataBase.selectedTrackDao()
            .getTrackIdOfPlaylistByPlaylistId(selectedTrack.playlistId)
        val amountTracks = listTrackIds.size

        playlist.listTrackIds = listTrackIds
        playlist.amountTracks = amountTracks

        val playlistEntityOut = playlistDBConvertor.mapModelToEntity(playlist)
        appDataBase.playlistDao().updatePlaylist(playlistEntityOut)
    }

    override suspend fun getPlayLists(): Flow<MutableList<Playlist>> = flow {
        val playLists = appDataBase.playlistDao().getPlaylists()
        emit(convertListOfPlaylistEntityToModel(playLists))
    }

    override suspend fun insertTrackToPlaylist(selectedTrack: SelectedTrack): Flow<Long> = flow {
        val trackIdsOfPlaylist =
            appDataBase.selectedTrackDao()
                .getTrackIdOfPlaylistByPlaylistId(selectedTrack.playlistId)
        if (trackIdsOfPlaylist.contains(selectedTrack.trackId)) {
            emit(FAIL_INSERT)
        } else {
            val selectedTrackEntity = selectedTrackDBConvertor.mapModelToEntity(selectedTrack)
            appDataBase.selectedTrackDao().insertTrackToPlaylist(selectedTrackEntity)

            updatePlaylist(selectedTrack)

            emit(SUCCESS_INSERT)
        }
    }

    private fun convertListOfPlaylistEntityToModel(playlists: MutableList<PlaylistEntity>): MutableList<Playlist> {
        return playlists.map { playlistEntity ->
            playlistDBConvertor.mapEntityToModel(playlistEntity)
        }.toMutableList()
    }

    companion object {
        const val SUCCESS_INSERT = 1L
        const val FAIL_INSERT = 0L
    }

}