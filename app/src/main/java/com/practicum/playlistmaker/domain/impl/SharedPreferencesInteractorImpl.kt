package com.practicum.playlistmaker.domain.impl

import com.practicum.playlistmaker.domain.api.ApiSharedPreferencesInteractor
import com.practicum.playlistmaker.domain.api.ApiSharedPreferencesRepository
import com.practicum.playlistmaker.domain.models.Track

class SharedPreferencesInteractorImpl(private val sharedPreferencesRepositoryInteractor: ApiSharedPreferencesRepository) :
    ApiSharedPreferencesInteractor {
    override fun saveSearchHistory(trackListHistory: MutableList<Track>) {
        sharedPreferencesRepositoryInteractor.saveSearchHistory(trackListHistory)
    }

    override fun readSearchHistory(): Array<Track>? {
        return sharedPreferencesRepositoryInteractor.readSearchHistory()
    }

    override fun cleanSearchHistory() {
        sharedPreferencesRepositoryInteractor.cleanSearchHistory()
    }

}