package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.models.Track


interface TrackHistoryStorage {
    fun saveSearchHistory(trackListHistory: MutableList<Track>)
    fun readSearchHistory(): MutableList<Track>
    fun cleanSearchHistory()
}