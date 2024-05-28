package com.practicum.playlistmaker.di

import com.google.gson.Gson
import com.practicum.playlistmaker.library.favorites.data.db.FavoritesRepositoryImpl
import com.practicum.playlistmaker.library.favorites.data.db.convertors.TrackDbConvertor
import com.practicum.playlistmaker.library.favorites.domain.db.FavoritesRepository
import com.practicum.playlistmaker.library.playlist.data.PlaylistRepositoryImpl
import com.practicum.playlistmaker.library.playlist.data.convertors.PlaylistDBConvertor
import com.practicum.playlistmaker.library.playlist.data.convertors.SelectedTrackDBConvertor
import com.practicum.playlistmaker.library.playlist.domain.PlaylistRepository
import com.practicum.playlistmaker.search.data.TrackRepositoryImpl
import com.practicum.playlistmaker.search.domain.api.TrackRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val reposiroryModule = module {

    single<TrackRepository> {
        TrackRepositoryImpl(
            networkClient = get(),
            context = androidContext(),
            appDataBase = get()
        )
    }

    single { TrackDbConvertor() }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(
            appDataBase = get(),
            trackDbConvertor = get()
        )
    }

    single<PlaylistDBConvertor> {
        PlaylistDBConvertor(gson = Gson())
    }

    single<PlaylistRepository> {
        PlaylistRepositoryImpl(
            appDataBase = get(),
            playlistDBConvertor = get(),
            selectedTrackDBConvertor = get(),
            androidContext()
        )
    }

    single { SelectedTrackDBConvertor() }

}