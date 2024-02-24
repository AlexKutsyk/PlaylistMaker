package com.practicum.playlistmaker.settings.domain

interface SettingsStorage {
    fun saveSettings(toggle: Boolean)
    fun getSettings(): Boolean
}