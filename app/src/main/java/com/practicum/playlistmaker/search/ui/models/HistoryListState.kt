package com.practicum.playlistmaker.search.ui.models

import com.practicum.playlistmaker.search.domain.models.Track

sealed interface HistoryListState {
    data class Empty(val tracks: MutableList<Track>): HistoryListState
    data class Content(val tracks: MutableList<Track>): HistoryListState
}