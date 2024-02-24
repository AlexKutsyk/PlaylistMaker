package com.practicum.playlistmaker.di

import android.content.Context
import com.practicum.playlistmaker.settings.data.SETTINGS
import com.practicum.playlistmaker.settings.data.SettingsStorageImpl
import com.practicum.playlistmaker.settings.domain.SettingsStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageSettingsModule = module {

//    single {
//        androidContext().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
//    }

    single<SettingsStorage> {
        SettingsStorageImpl(
            androidContext(),
            androidContext().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        )
    }
}