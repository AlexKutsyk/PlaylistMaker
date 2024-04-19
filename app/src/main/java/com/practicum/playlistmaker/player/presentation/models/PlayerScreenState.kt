package com.practicum.playlistmaker.player.presentation.models

import com.practicum.playlistmaker.search.domain.models.Track


sealed interface PlayerScreenState {
    object Loading : PlayerScreenState
    data class NoDemo(val track: Track) : PlayerScreenState
    data class Content(val track: Track) : PlayerScreenState
}