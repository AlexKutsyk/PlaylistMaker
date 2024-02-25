package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.player.ui.PlayerViewModel
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.ui.SearchViewModel
import com.practicum.playlistmaker.settings.ui.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { (track: Track) ->
        PlayerViewModel(track)
    }

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        SettingsViewModel(get(), get())
    }

}