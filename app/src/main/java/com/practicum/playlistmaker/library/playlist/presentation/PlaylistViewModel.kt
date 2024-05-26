package com.practicum.playlistmaker.library.playlist.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.library.playlist.domain.PlaylistInteractor
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import com.practicum.playlistmaker.library.playlist.presentation.models.DeleteState
import com.practicum.playlistmaker.library.playlist.presentation.models.TracksOfPlaylistState
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.sharing.domain.SharingInteractor
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

class PlaylistViewModel(
    private val playlistInteractor: PlaylistInteractor,
    private val sharingInteractor: SharingInteractor,
    val context: Context,
) : ViewModel() {

    private var playlistState = MutableLiveData<Playlist>()
    fun getPlaylistState(): LiveData<Playlist> = playlistState

    private var listTracksOfPlaylistState = MutableLiveData<TracksOfPlaylistState>()
    fun getListTracksOfPlaylistState(): LiveData<TracksOfPlaylistState> = listTracksOfPlaylistState

    private var stateDeleteLiveData = MutableLiveData<DeleteState>()
    fun getStateDelete(): LiveData<DeleteState> = stateDeleteLiveData

    fun getPlaylist(playlistId: Int) {
        viewModelScope.launch {
            playlistInteractor.getPlaylist(playlistId).collect { playlist ->
                playlistState.postValue(playlist)
            }
        }
    }

    fun getTracksByPlaylistId(playlistId: Int) {
        viewModelScope.launch {
            playlistInteractor.getTracksByPlaylistId(playlistId).collect { listTrackOfPlaylist ->
                if (listTrackOfPlaylist.isEmpty()) {
                    listTracksOfPlaylistState.postValue(TracksOfPlaylistState.EmptyPlaylist)
                } else {
                    listTracksOfPlaylistState.postValue(
                        TracksOfPlaylistState.ContentPlaylist(
                            listTrackOfPlaylist
                        )
                    )
                }
            }
        }
    }

    fun deleteSelectedTrackFromPlaylist(track: Track) {
        viewModelScope.launch {
            playlistInteractor.deleteSelectedTrackFromPlaylist(track)
            getTracksByPlaylistId(track.playlistId)
            getPlaylist(track.playlistId)
        }
    }

    fun shareLinkPlaylist(playlistId: Int) {
        viewModelScope.launch {
            val message = buildMessage(playlistId)
            sharingInteractor.shareApp(message)
        }
    }

    private suspend fun buildMessage(playlistId: Int): String {
        var textMessage = ""
        viewModelScope.async {
            playlistInteractor.getPlaylist(playlistId).collect { playlist ->
                textMessage =
                    "${playlist.namePlaylist}\n${playlist.descriptionPlaylist}\n${playlist.amountTracks} ${playlist.trackSpelling}"
            }
            playlistInteractor.getTracksByPlaylistId(playlistId).collect { listTracksOfPlaylist ->
                var indexTrack = 0
                listTracksOfPlaylist.forEach { track ->
                    indexTrack += 1
                    textMessage += "\n${indexTrack}. ${track.artistName} - ${track.trackName} (${track.trackTimeMillis?.milliseconds?.inWholeMinutes}:" + SimpleDateFormat(
                        context.getString(R.string.ss_second) + ")",
                        Locale.getDefault()
                    ).format(track.trackTimeMillis)
                }
            }
        }.await()
        return textMessage
    }

    fun deletePlaylistById(playlistId: Int) {
        viewModelScope.launch {
            playlistInteractor.deletePlaylistById(playlistId).collect { isCompleteDelete ->
                if (isCompleteDelete == 1) stateDeleteLiveData.postValue(DeleteState(true))
            }
        }
    }

}