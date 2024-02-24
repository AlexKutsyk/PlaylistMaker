package com.practicum.playlistmaker.settings.domain

interface SettingsInteractor {
    fun saveSettings(toggle: Boolean)
    fun getSettings(): Boolean
}