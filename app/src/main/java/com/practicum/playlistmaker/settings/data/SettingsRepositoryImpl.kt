package com.practicum.playlistmaker.settings.data

import android.content.Context
import com.practicum.playlistmaker.settings.domain.SettingsRepository

const val SETTINGS = "shared_preferences_settings"
const val SWITCH_KEY = "key_for_switch"

class SettingsRepositoryImpl(val context: Context) : SettingsRepository {

    private val sharedPrefs = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

    override fun saveSettings(toggle: Boolean) {
        sharedPrefs.edit().putBoolean(SWITCH_KEY, toggle).apply()
    }

    override fun getSettings(): Boolean {
        return sharedPrefs.getBoolean(SWITCH_KEY, true)
    }
}