package com.practicum.playlistmaker.player.presentation.models

sealed class InsertTrackState(val namePlaylist: String) {
    class Success(namePlaylist: String) : InsertTrackState(namePlaylist)
    class Fail(namePlaylist: String) : InsertTrackState(namePlaylist)
}