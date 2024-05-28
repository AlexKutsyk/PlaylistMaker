package com.practicum.playlistmaker.library.playlist.data

import android.content.Context
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.library.favorites.data.db.AppDataBase
import com.practicum.playlistmaker.library.playlist.data.convertors.PlaylistDBConvertor
import com.practicum.playlistmaker.library.playlist.data.convertors.SelectedTrackDBConvertor
import com.practicum.playlistmaker.library.playlist.data.entity.PlaylistEntity
import com.practicum.playlistmaker.library.playlist.data.entity.SelectedTrackEntity
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import com.practicum.playlistmaker.library.playlist.domain.PlaylistRepository
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.milliseconds

class PlaylistRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val playlistDBConvertor: PlaylistDBConvertor,
    private val selectedTrackDBConvertor: SelectedTrackDBConvertor,
    private val context: Context
) : PlaylistRepository {

    override suspend fun insertPlaylist(playlist: Playlist) {
        val playlistEntity = playlistDBConvertor.mapModelToEntity(playlist)
        appDataBase.playlistDao().insertPlaylist(playlistEntity)
    }

    override suspend fun insertTrackToPlaylist(track: Track): Flow<Long> = flow {
        val trackIdsOfPlaylist =
            appDataBase.selectedTrackDao()
                .getListTrackIdOfPlaylistByPlaylistId(track.playlistId)
        if (trackIdsOfPlaylist.contains(track.trackId)) {
            emit(FAIL_INSERT)
        } else {

            val selectedTrackEntity = selectedTrackDBConvertor.mapModelToEntity(track)
            appDataBase.selectedTrackDao().insertTrackToPlaylist(selectedTrackEntity)

            updatePlaylist(track)

            emit(SUCCESS_INSERT)
        }
    }

    private suspend fun updatePlaylist(track: Track) {

        val playlistEntity = appDataBase.playlistDao().getPlaylistById(track.playlistId)
        val playlist = playlistDBConvertor.mapEntityToModel(playlistEntity)

        val listTrackIds = appDataBase.selectedTrackDao()
            .getListTrackIdOfPlaylistByPlaylistId(track.playlistId)
        val amountTracks = listTrackIds.size
        val listTracksTime =
            appDataBase.selectedTrackDao().getListTrackTimeOfPlaylistById(track.playlistId)
        val totalTimePlaylist = listTracksTime.sum()

        playlist.listTrackIds = listTrackIds
        playlist.amountTracks = amountTracks
        playlist.trackSpelling = chooseSpellingTrack(amountTracks)
        playlist.totalPlaylistTime = totalTimePlaylist
        playlist.minutesSpelling = chooseSpellingMinutes(playlist.totalPlaylistTime)

        val playlistEntityNew = playlistDBConvertor.mapModelToEntity(playlist)
        appDataBase.playlistDao().updatePlaylist(playlistEntityNew)
    }

    override suspend fun getListPlayLists(): Flow<MutableList<Playlist>> = flow {
        val playLists = appDataBase.playlistDao().getPlaylists()
        emit(convertListOfPlaylistEntityToModel(playLists))
    }

    override suspend fun getPlaylist(playlistId: Int): Flow<Playlist> = flow {
        val playlistEntity = appDataBase.playlistDao().getPlaylistById(playlistId)
        emit(playlistDBConvertor.mapEntityToModel(playlistEntity))
    }

    override suspend fun getTracksByPlaylistId(playlistId: Int): Flow<MutableList<Track>> = flow {
        val listTracksOfPlaylistEntity =
            appDataBase.selectedTrackDao().getTracksByPlaylistId(playlistId)
        emit(convertListOfTracksEntityToModel(listTracksOfPlaylistEntity))
    }

    override suspend fun deleteSelectedTrackFromPlaylist(track: Track) {
        val deletedTrackEntity = selectedTrackDBConvertor.mapModelToEntity(track)
        appDataBase.selectedTrackDao().deleteSelectedTrackFromPlaylist(deletedTrackEntity)
        updatePlaylist(track)
    }

    override suspend fun deletePlaylistById(playlistId: Int): Flow<Int> = flow {
        val playlistEntity = appDataBase.playlistDao().getPlaylistById(playlistId)
        appDataBase.playlistDao().deletePlaylist(playlistEntity)
        emit(SUCCESS_DELETE)
    }

    override suspend fun saveUpdatesPlaylist(playlist: Playlist) {
        val playlistEntity = playlistDBConvertor.mapModelToEntity(playlist)
        appDataBase.playlistDao().updatePlaylist(playlistEntity)
    }

    private fun convertListOfPlaylistEntityToModel(playlists: MutableList<PlaylistEntity>): MutableList<Playlist> {
        return playlists.map { playlistEntity ->
            playlistDBConvertor.mapEntityToModel(playlistEntity)
        }.toMutableList()
    }

    private fun convertListOfTracksEntityToModel(listTracksEntity: MutableList<SelectedTrackEntity>): MutableList<Track> {
        return listTracksEntity.map { selectedTrackEntity ->
            selectedTrackDBConvertor.mapEntityToModel(selectedTrackEntity)
        }.toMutableList()
    }

    private fun chooseSpellingTrack(amountTracks: Int): String {
        return if (amountTracks % 100 in 11..19) {
            context.getString(R.string.trackov)
        } else {
            when (amountTracks % 10) {
                1 -> context.getString(R.string.track)
                2, 3, 4 -> context.getString(R.string.tracka)
                else -> context.getString(R.string.trackov)
            }
        }
    }

    private fun chooseSpellingMinutes(totalPlaylistTime: Int): String {
        val timeInMinutes = totalPlaylistTime.milliseconds.inWholeMinutes
        return if (timeInMinutes % 100 in 11..19) {
            context.getString(R.string.minut)
        } else {
            when (timeInMinutes % 10) {
                1L -> context.getString(R.string.minuta)
                2L, 3L, 4L -> context.getString(R.string.minuti)
                else -> context.getString(R.string.minut)
            }
        }
    }

    companion object {
        const val SUCCESS_INSERT = 1L
        const val FAIL_INSERT = 0L
        const val SUCCESS_DELETE = 1

    }

}