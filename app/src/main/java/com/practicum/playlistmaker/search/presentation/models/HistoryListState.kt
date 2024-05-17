package com.practicum.playlistmaker.search.presentation.models

import com.practicum.playlistmaker.search.domain.models.Track

sealed class HistoryListState(
    val listHistory: MutableList<Track>,
    val isVisibleHistoryList: Boolean,
    val isVisibleError: Boolean
) {
    class Content(listHistory: MutableList<Track>) : HistoryListState(
        listHistory,
        true,
        false
    )
    class Invisible() : HistoryListState(
        emptyList<Track>().toMutableList(),
        false,
        false
    )
}