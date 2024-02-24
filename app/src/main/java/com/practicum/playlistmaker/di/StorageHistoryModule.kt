package com.practicum.playlistmaker.di

import android.content.Context
import com.google.gson.Gson
import com.practicum.playlistmaker.search.data.local.STORAGE
import com.practicum.playlistmaker.search.data.local.SharedPreferencesHistorySearchStorageImpl
import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesHistorySearchStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageHistoryModule = module {

    single<ApiSharedPreferencesHistorySearchStorage> {
        SharedPreferencesHistorySearchStorageImpl(androidContext(), androidContext().getSharedPreferences(STORAGE, Context.MODE_PRIVATE), Gson())
    }

//    single {
//        androidContext().getSharedPreferences(STORAGE, Context.MODE_PRIVATE)
//    }
//
//    single {
//        Gson()
//    }

}