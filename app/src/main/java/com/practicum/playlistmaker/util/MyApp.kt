package com.practicum.playlistmaker.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.di.dataModule
import com.practicum.playlistmaker.di.interactorModule
import com.practicum.playlistmaker.di.navigationModule
import com.practicum.playlistmaker.di.reposiroryModule
import com.practicum.playlistmaker.di.storageHistoryModule
import com.practicum.playlistmaker.di.storageSettingsModule
import com.practicum.playlistmaker.di.viewModelModule
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp() : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(
                dataModule,
                reposiroryModule,
                interactorModule,
                viewModelModule,
                navigationModule,
                storageHistoryModule,
                storageSettingsModule
            )
        }
        val providerSetting by inject<SettingsInteractor>()
        val darkThemeEnabled = providerSetting.getSettings()
        switchTheme(darkThemeEnabled)
    }

    private fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}