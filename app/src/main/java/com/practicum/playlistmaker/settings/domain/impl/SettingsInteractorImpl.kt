package com.practicum.playlistmaker.settings.domain.impl

import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.SettingsRepository

class SettingsInteractorImpl(private val settingsRepository: SettingsRepository) : SettingsInteractor {
    override fun saveSettings(toggle: Boolean) {
        settingsRepository.saveSettings(toggle)
    }

    override fun getSettings(): Boolean {
        return settingsRepository.getSettings()
    }
}