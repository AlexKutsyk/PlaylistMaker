package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.library.data.db.FavoritesRepositoryImpl
import com.practicum.playlistmaker.library.data.db.convertors.TrackDbConvertor
import com.practicum.playlistmaker.library.domain.db.FavoritesRepository
import com.practicum.playlistmaker.search.data.TrackRepositoryImpl
import com.practicum.playlistmaker.search.domain.api.TrackRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val reposiroryModule = module{

    single<TrackRepository> {
        TrackRepositoryImpl(networkClient = get(), context = androidContext(), appDataBase = get())
    }

    single { TrackDbConvertor() }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(appDataBase = get(), trackDbConvertor = get())
    }

}