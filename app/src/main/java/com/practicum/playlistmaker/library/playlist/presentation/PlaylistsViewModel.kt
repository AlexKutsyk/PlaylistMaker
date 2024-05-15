package com.practicum.playlistmaker.library.playlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.library.playlist.domain.PlaylistInteractor
import com.practicum.playlistmaker.library.playlist.presentation.models.PlaylistState
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    val playlistCreatorInteractor: PlaylistInteractor,
) : ViewModel() {

    private var statePlaylist = MutableLiveData<PlaylistState>()
    fun getStatePlaylist(): LiveData<PlaylistState> = statePlaylist

    fun getPlaylistFromDB() {
        viewModelScope.launch {
            playlistCreatorInteractor.getPlaylists().collect { playlists ->
                if (playlists.isNullOrEmpty()) {
                    statePlaylist.postValue(PlaylistState.Empty())
                } else {
                    statePlaylist.postValue(PlaylistState.Content(playlists))
                }
            }
        }
    }
}