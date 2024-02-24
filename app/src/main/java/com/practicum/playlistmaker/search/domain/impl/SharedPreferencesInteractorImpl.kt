package com.practicum.playlistmaker.search.domain.impl

import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesInteractor
import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesRepository
import com.practicum.playlistmaker.search.domain.models.Track


class SharedPreferencesInteractorImpl(private val sharedPreferencesRepository: ApiSharedPreferencesRepository) :
    ApiSharedPreferencesInteractor {
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