package com.practicum.playlistmaker.settings.domain

interface SettingsRepository {
    fun saveSettings(toggle: Boolean)
    fun getSettings(): Boolean
}