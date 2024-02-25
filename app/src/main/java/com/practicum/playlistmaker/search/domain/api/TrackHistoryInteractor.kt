package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.models.Track


interface TrackHistoryInteractor {
    fun saveSearchHistory(trackListHistory: MutableList<Track>)
    fun readSearchHistory(): Array<Track>?
    fun cleanSearchHistory()
}