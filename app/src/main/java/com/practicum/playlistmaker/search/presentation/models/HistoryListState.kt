package com.practicum.playlistmaker.search.presentation.models

import com.practicum.playlistmaker.search.domain.models.Track

sealed class HistoryListState(
    val tracks: MutableList<Track>?,
    val isVisibleHistoryList: Boolean,
    val isVisibleError: Boolean
) {
    class Empty(tracks: MutableList<Track>) : HistoryListState(tracks, true, false)
    class Content(tracks: MutableList<Track>) : HistoryListState(tracks, true, false)
    class Invisible() : HistoryListState(null, false, false)
}