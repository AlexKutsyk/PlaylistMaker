package com.practicum.playlistmaker.search.presentation.models

import com.practicum.playlistmaker.search.domain.models.Track

sealed class TrackState(
    val tracks: List<Track>?,
    val isVisibleLoading: Boolean,
    val isVisibleContent: Boolean,
    val isVisibleError: Boolean,
    val isVisibleErrorConection: Boolean
) {
    class Loading : TrackState(
        null,
        true,
        false,
        false,
        false
    )

    class Content(
        tracks: List<Track>,
        isVisibleContent: Boolean
    ) :
        TrackState(
            tracks,
            false,
            isVisibleContent,
            false,
            false
        )

    class Error : TrackState(
        null,
        false,
        false,
        true,
        false
    )

    class ErrorConnect : TrackState(
        null,
        false,
        false,
        false,
        true
    )
}