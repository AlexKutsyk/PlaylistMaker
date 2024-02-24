package com.practicum.playlistmaker.search.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesHistorySearchStorage
import com.practicum.playlistmaker.search.domain.models.Track

const val STORAGE = "shared_preferences_storage"
const val SEARCH_HISTORY_KEY = "key_for_search_history"

class SharedPreferencesHistorySearchStorageImpl(
    val context: Context,
    private val sharedPrefs: SharedPreferences,
    private val gson: Gson,
    ) : ApiSharedPreferencesHistorySearchStorage {

    override fun saveSearchHistory(trackListHistory: MutableList<Track>) {
        val trackListHistoryJson: String = gson.toJson(trackListHistory)
        sharedPrefs.edit()
            .putString(SEARCH_HISTORY_KEY, trackListHistoryJson)
            .apply()
    }

    override fun readSearchHistory(): Array<Track>? {
        val trackListHistoryJson: String =
            sharedPrefs.getString(SEARCH_HISTORY_KEY, null)
                ?: return emptyArray()
        return gson.fromJson(trackListHistoryJson, Array<Track>::class.java)
    }

    override fun cleanSearchHistory() {
        sharedPrefs.edit()
            .clear()
            .apply()
    }
}