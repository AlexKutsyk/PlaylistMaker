package com.practicum.playlistmaker.settings.data

import android.content.Context
import android.content.SharedPreferences
import com.practicum.playlistmaker.settings.domain.SettingsStorage

const val SETTINGS = "shared_preferences_settings"
const val SWITCH_KEY = "key_for_switch"

class SettingsStorageImpl(
    val context: Context,
    private val sharedPrefs: SharedPreferences,
) : SettingsStorage {

    override fun saveSettings(toggle: Boolean) {
        sharedPrefs.edit().putBoolean(SWITCH_KEY, toggle).apply()
    }

    override fun getSettings(): Boolean {
        return sharedPrefs.getBoolean(SWITCH_KEY, true)
    }
}