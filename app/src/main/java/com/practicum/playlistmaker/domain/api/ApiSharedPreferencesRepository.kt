package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.Track

interface ApiSharedPreferencesRepository {
    fun saveSearchHistory(trackListHistory: MutableList<Track>)
    fun readSearchHistory(): Array<Track>?
    fun cleanSearchHistory()
}