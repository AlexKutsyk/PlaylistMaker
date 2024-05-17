package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.library.favorites.presentation.FavoritesViewModel
import com.practicum.playlistmaker.library.playlist.presentation.PlaylistCreatorViewModel
import com.practicum.playlistmaker.library.playlist.presentation.PlaylistsViewModel
import com.practicum.playlistmaker.player.presentation.PlayerViewModel
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.presentation.SearchViewModel
import com.practicum.playlistmaker.settings.ui.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { (track: Track) ->
        PlayerViewModel(
            track = track,
            context = androidContext(),
            favoritesInteractor = get(),
            playlistInteractor = get()
        )
    }

    viewModel {
        SearchViewModel(
            get(),
            get()
        )
    }

    viewModel {
        SettingsViewModel(
            get(),
            get()
        )
    }

    viewModel {
        FavoritesViewModel(favoritesInteractor = get())
    }

    viewModel {
        PlaylistCreatorViewModel(playlistCreatorInteractor = get())
    }

    viewModel {
        PlaylistsViewModel(playlistCreatorInteractor = get())
    }

}