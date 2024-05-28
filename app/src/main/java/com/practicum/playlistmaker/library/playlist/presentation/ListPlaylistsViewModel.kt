package com.practicum.playlistmaker.library.playlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.library.playlist.domain.PlaylistInteractor
import com.practicum.playlistmaker.library.playlist.presentation.models.PlaylistState
import kotlinx.coroutines.launch

class ListPlaylistsViewModel(
    val playlistCreatorInteractor: PlaylistInteractor,
) : ViewModel() {

    private var listPlaylistsState = MutableLiveData<PlaylistState>()
    fun getListPlaylistsState(): LiveData<PlaylistState> = listPlaylistsState

    fun getListPlaylistsFromDB() {
        viewModelScope.launch {
            playlistCreatorInteractor.getListPlaylists().collect { playlists ->
                if (playlists.isEmpty()) {
                    listPlaylistsState.postValue(PlaylistState.Empty())
                } else {
                    listPlaylistsState.postValue(PlaylistState.Content(playlists))
                }
            }
        }
    }
}