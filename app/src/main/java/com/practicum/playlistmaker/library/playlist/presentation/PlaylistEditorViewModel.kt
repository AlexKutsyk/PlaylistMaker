package com.practicum.playlistmaker.library.playlist.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.library.playlist.domain.PlaylistInteractor
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import kotlinx.coroutines.launch

class PlaylistEditorViewModel(
    val playlistId: Int,
    private val playlistCreatorInteractor: PlaylistInteractor,
) : PlaylistCreatorViewModel(playlistCreatorInteractor) {

    init {
        getPlayListById(playlistId)
    }

    private var playlistState = MutableLiveData<Playlist>()
    fun getPlaylistState(): LiveData<Playlist> = playlistState

    private fun getPlayListById(playlistId: Int) {
        viewModelScope.launch {
            playlistCreatorInteractor.getPlaylist(playlistId).collect { playlist ->
                playlistState.postValue(playlist)
            }
        }
    }

    fun saveUpdatesPlaylist(
        playlist: Playlist,
        namePlaylist: String,
        description: String?,
        uriImageStorage: Uri?,
    ) {
        viewModelScope.launch {
            val editedPlaylist = editPlaylist(
                playlist,
                namePlaylist,
                description,
                uriImageStorage
            )
            playlistCreatorInteractor.saveUpdatesPlaylist(editedPlaylist)
        }
    }

    private fun editPlaylist(
        playlist: Playlist,
        namePlaylist: String,
        description: String?,
        uriImageStorage: Uri?,
    ): Playlist {
        playlist.namePlaylist = namePlaylist
        playlist.descriptionPlaylist = description
        playlist.uriImageStorage = uriImageStorage
        return playlist
    }
}