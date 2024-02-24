package com.practicum.playlistmaker.search.domain.impl

import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesHistorySearchInteractor
import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesHistorySearchStorage
import com.practicum.playlistmaker.search.domain.models.Track


class SharedPreferencesHistorySearchInteractorImpl(
    private val sharedPreferencesRepository: ApiSharedPreferencesHistorySearchStorage
) : ApiSharedPreferencesHistorySearchInteractor {
    override fun saveSearchHistory(trackListHistory: MutableList<Track>) {
        sharedPreferencesRepository.saveSearchHistory(trackListHistory)
    }

    override fun readSearchHistory(): Array<Track>? {
        return sharedPreferencesRepository.readSearchHistory()
    }

    override fun cleanSearchHistory() {
        sharedPreferencesRepository.cleanSearchHistory()
    }

}