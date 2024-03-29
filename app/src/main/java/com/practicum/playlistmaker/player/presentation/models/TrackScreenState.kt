package com.practicum.playlistmaker.player.presentation.models

import com.practicum.playlistmaker.search.domain.models.Track


sealed interface TrackScreenState {

    object Loading: TrackScreenState
    data class NoDemo(val track: Track): TrackScreenState
    data class Content(val track: Track): TrackScreenState
}