package com.practicum.playlistmaker.util

import android.content.Context
import com.practicum.playlistmaker.search.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.search.data.shared_preferences.SharedPreferencesRepositoryImpl
import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesInteractor
import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesRepository
import com.practicum.playlistmaker.search.data.TrackRepositoryImpl
import com.practicum.playlistmaker.search.domain.api.ApiTrackInteractor
import com.practicum.playlistmaker.search.domain.api.ApiTrackRepository
import com.practicum.playlistmaker.search.domain.impl.SharedPreferencesInteractorImpl
import com.practicum.playlistmaker.search.domain.impl.TrackInteractorImpl
import com.practicum.playlistmaker.settings.data.SettingsRepositoryImpl
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.SettingsRepository
import com.practicum.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.practicum.playlistmaker.sharing.data.ExternalNavigatorImpl
import com.practicum.playlistmaker.sharing.domain.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.SharingInteractor
import com.practicum.playlistmaker.sharing.domain.impl.SharingInteractorImpl


object Creator {
    private fun provideTrackRepository(context: Context): ApiTrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient(context), context)
    }
    fun provideTrackInteractor(context: Context): ApiTrackInteractor {
        return TrackInteractorImpl(provideTrackRepository(context))
    }

    private fun provideSharedPreferencesRepository(context: Context) : ApiSharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(context)
    }
    fun provideSharedPreferencesInteractor(context: Context) : ApiSharedPreferencesInteractor {
        return SharedPreferencesInteractorImpl(provideSharedPreferencesRepository(context))
    }

    fun provideSettingsInteractor(context: Context): SettingsInteractor {
        return SettingsInteractorImpl(provideSettingsRepository(context))
    }

    private fun provideSettingsRepository(context: Context): SettingsRepository {
        return SettingsRepositoryImpl(context)
    }

    fun provideSharingInteractor(context: Context): SharingInteractor {
        return SharingInteractorImpl(provideExternalNavigator(context), context)
    }

    private fun provideExternalNavigator(context: Context): ExternalNavigator {
        return ExternalNavigatorImpl(context)
    }
}