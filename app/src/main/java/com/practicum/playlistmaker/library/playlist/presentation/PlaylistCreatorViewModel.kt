package com.practicum.playlistmaker.library.playlist.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.library.playlist.domain.PlaylistInteractor
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import kotlinx.coroutines.launch

open class PlaylistCreatorViewModel(
    private val playlistCreatorInteractor: PlaylistInteractor,
) : ViewModel() {

    fun createPlaylist(
        namePlaylist: String,
        descriptionPlaylist: String?,
        uriImageStorage: Uri?,
    ) {
        viewModelScope.launch {
            val playlist = invokePlaylist(
                namePlaylist,
                descriptionPlaylist,
                uriImageStorage
            )
            playlistCreatorInteractor.insertPlaylist(playlist)
        }
    }

    private fun invokePlaylist(
        namePlaylist: String,
        descriptionPlaylist: String?,
        uriImageStorage: Uri?
    ): Playlist = Playlist(
        namePlaylist = namePlaylist,
        descriptionPlaylist = descriptionPlaylist,
        uriImageStorage = uriImageStorage
    )


}