package com.practicum.playlistmaker.settings.domain.impl

import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.SettingsStorage

class SettingsInteractorImpl(
    private val settingsStorage: SettingsStorage
) : SettingsInteractor {

    override fun saveSettings(toggle: Boolean) {
        settingsStorage.saveSettings(toggle)
    }

    override fun getSettings(): Boolean {
        return settingsStorage.getSettings()
    }
}