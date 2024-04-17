package com.practicum.playlistmaker.search.presentation.models

import com.practicum.playlistmaker.search.domain.models.Track

sealed interface TrackState {
    object Loading: TrackState
    data class Content(val tracks: List<Track>): TrackState
    object Error: TrackState
    object ErrorConnect: TrackState
}