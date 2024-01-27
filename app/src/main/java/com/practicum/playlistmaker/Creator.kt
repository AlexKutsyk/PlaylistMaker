package com.practicum.playlistmaker

import android.content.Intent
import com.practicum.playlistmaker.data.TrackIntentRepositoryImpl
import com.practicum.playlistmaker.data.TrackRepositoryImpl
import com.practicum.playlistmaker.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.domain.api.ApiGetTrackUseCase
import com.practicum.playlistmaker.domain.api.TrackIntentRepository
import com.practicum.playlistmaker.domain.api.TrackInteractor
import com.practicum.playlistmaker.domain.api.TrackRepository
import com.practicum.playlistmaker.domain.impl.GetTrackUseCaseImpl
import com.practicum.playlistmaker.domain.impl.TrackInteractorImpl

object Creator {
    private fun provideTrackRepository(): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient())
    }
    fun provideTrackInteractor(): TrackInteractor {
        return TrackInteractorImpl(provideTrackRepository())
    }

    private fun provideTrackIntentRepository(): TrackIntentRepository {
        return TrackIntentRepositoryImpl(Intent())
    }

    fun provideGetTrackUseCase(): ApiGetTrackUseCase {
        return GetTrackUseCaseImpl(provideTrackIntentRepository())
    }
}