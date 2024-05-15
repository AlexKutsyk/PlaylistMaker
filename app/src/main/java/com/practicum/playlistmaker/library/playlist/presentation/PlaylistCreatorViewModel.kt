package com.practicum.playlistmaker.library.playlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.library.playlist.domain.PlaylistInteractor
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import kotlinx.coroutines.launch
import java.io.File

class PlaylistCreatorViewModel(
    private val playlistCreatorInteractor: PlaylistInteractor,
) : ViewModel() {

    fun createPlaylist(
        namePlaylist: String,
        descriptionPlaylist: String?,
        pathImageCover: File?,
    ) {
        viewModelScope.launch {
            val playlist = invokePlaylist(
                namePlaylist,
                descriptionPlaylist,
                pathImageCover
            )
            playlistCreatorInteractor.insertPlaylist(playlist)
        }
    }

    private fun invokePlaylist(
        namePlaylist: String,
        descriptionPlaylist: String?,
        pathImageCover: File?
    ): Playlist = Playlist(
        namePlaylist = namePlaylist,
        descriptionPlaylist = descriptionPlaylist,
        pathImageCover = pathImageCover
    )
}