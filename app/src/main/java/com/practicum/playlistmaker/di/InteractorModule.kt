package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesHistorySearchInteractor
import com.practicum.playlistmaker.search.domain.api.ApiTrackInteractor
import com.practicum.playlistmaker.search.domain.impl.SharedPreferencesHistorySearchInteractorImpl
import com.practicum.playlistmaker.search.domain.impl.TrackInteractorImpl
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.practicum.playlistmaker.sharing.domain.SharingInteractor
import com.practicum.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val interactorModule = module {

    single <ApiTrackInteractor> {
        TrackInteractorImpl(get())
    }

    single <ApiSharedPreferencesHistorySearchInteractor> {
        SharedPreferencesHistorySearchInteractorImpl(get())
    }

    single <SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    single <SharingInteractor> {
        SharingInteractorImpl(get(), androidContext())
    }
}