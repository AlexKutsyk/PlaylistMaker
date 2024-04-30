package com.practicum.playlistmaker.search.domain.impl

import com.practicum.playlistmaker.search.domain.api.TrackHistoryInteractor
import com.practicum.playlistmaker.search.domain.api.TrackHistoryStorage
import com.practicum.playlistmaker.search.domain.models.Track


class TrackHistoryInteractorImpl(
    private val trackHistoryStorage: TrackHistoryStorage
) : TrackHistoryInteractor {
    override fun saveSearchHistory(trackListHistory: MutableList<Track>) {
        trackHistoryStorage.saveSearchHistory(trackListHistory)
    }

    override fun readSearchHistory(): MutableList<Track> {
        return trackHistoryStorage.readSearchHistory()
    }

    override fun cleanSearchHistory() {
        trackHistoryStorage.cleanSearchHistory()
    }

}