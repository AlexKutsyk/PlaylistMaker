package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.Track

interface ApiSharedPreferencesInteractor {
    fun saveSearchHistory(trackListHistory: MutableList<Track>)
    fun readSearchHistory(): Array<Track>?
    fun cleanSearchHistory()
}