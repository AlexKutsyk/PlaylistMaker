package com.practicum.playlistmaker.settings.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.util.Creator

class MyApp(): Application() {

    override fun onCreate() {
        super.onCreate()
        val providerSetting = Creator.provideSettingsInteractor(applicationContext)
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