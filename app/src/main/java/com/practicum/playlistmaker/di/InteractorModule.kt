package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.library.favorites.domain.db.FavoritesInteractor
import com.practicum.playlistmaker.library.favorites.domain.db.impl.FavoritesInteractorImpl
import com.practicum.playlistmaker.library.playlist.domain.PlaylistInteractor
import com.practicum.playlistmaker.library.playlist.domain.impl.PlaylistInteractorImpl
import com.practicum.playlistmaker.search.domain.api.TrackHistoryInteractor
import com.practicum.playlistmaker.search.domain.api.TrackInteractor
import com.practicum.playlistmaker.search.domain.impl.TrackHistoryInteractorImpl
import com.practicum.playlistmaker.search.domain.impl.TrackInteractorImpl
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.practicum.playlistmaker.sharing.domain.SharingInteractor
import com.practicum.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    factory <TrackInteractor> {
        TrackInteractorImpl(get())
    }

    factory <TrackHistoryInteractor> {
        TrackHistoryInteractorImpl(get())
    }

    factory <SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    factory <SharingInteractor> {
        SharingInteractorImpl(get())
    }

    factory <FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    factory <PlaylistInteractor> {
        PlaylistInteractorImpl(playlistRepository = get())
    }
}