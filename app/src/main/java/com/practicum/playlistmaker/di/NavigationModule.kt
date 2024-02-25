package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.sharing.data.ExternalNavigatorImpl
import com.practicum.playlistmaker.sharing.domain.ExternalNavigator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val navigationModule = module{

    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
}