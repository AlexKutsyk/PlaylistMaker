package com.practicum.playlistmaker.di

import android.content.Context
import com.google.gson.Gson
import com.practicum.playlistmaker.search.data.TrackRepositoryImpl
import com.practicum.playlistmaker.search.data.local.STORAGE
import com.practicum.playlistmaker.search.data.local.SharedPreferencesHistorySearchStorageImpl
import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesHistorySearchStorage
import com.practicum.playlistmaker.search.domain.api.ApiTrackRepository
import com.practicum.playlistmaker.settings.data.SETTINGS
import com.practicum.playlistmaker.settings.data.SettingsStorageImpl
import com.practicum.playlistmaker.settings.domain.SettingsStorage
import com.practicum.playlistmaker.sharing.data.ExternalNavigatorImpl
import com.practicum.playlistmaker.sharing.domain.ExternalNavigator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val reposiroryModule = module{

    single<ApiTrackRepository> {
        TrackRepositoryImpl(get(), androidContext())
    }

}