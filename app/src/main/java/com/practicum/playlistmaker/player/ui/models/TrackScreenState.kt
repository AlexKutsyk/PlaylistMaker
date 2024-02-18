package com.practicum.playlistmaker.player.ui.models

import com.practicum.playlistmaker.search.domain.models.Track


sealed interface TrackScreenState {

    object Loading: TrackScreenState

    data class Content(val track: Track): TrackScreenState
}