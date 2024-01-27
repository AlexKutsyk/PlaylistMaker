package com.practicum.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmaker.domain.models.Track

const val STORAGE = "shared_preferences_storage"
const val SEARCH_HISTORY_KEY = "key_for_search_history"

class SearchHistory(private val sharedPrefs: SharedPreferences) {

    fun saveSearchHistory(trackListHistory: MutableList<Track>) {
        val trackListHistoryJson: String = Gson().toJson(trackListHistory)
        sharedPrefs.edit()
            .putString(SEARCH_HISTORY_KEY, trackListHistoryJson)
            .apply()
    }

    fun readSearchHistory(): Array<Track>? {
        val trackListHistoryJson: String = sharedPrefs.getString(SEARCH_HISTORY_KEY, "")?: return emptyArray()
        return Gson().fromJson(trackListHistoryJson, Array<Track>::class.java)
    }

    fun cleanSearchHistory() {
        sharedPrefs.edit()
            .clear()
            .apply()
    }
}