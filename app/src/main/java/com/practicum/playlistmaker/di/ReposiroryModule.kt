package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.search.data.TrackRepositoryImpl
import com.practicum.playlistmaker.search.domain.api.TrackRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val reposiroryModule = module{

    single<TrackRepository> {
        TrackRepositoryImpl(get(), androidContext())
    }

}