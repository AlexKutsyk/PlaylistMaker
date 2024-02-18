package com.practicum.playlistmaker.search.data.shared_preferences

import android.content.Context
import com.google.gson.Gson
import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesRepository
import com.practicum.playlistmaker.search.domain.models.Track

const val STORAGE = "shared_preferences_storage"
const val SEARCH_HISTORY_KEY = "key_for_search_history"

class SharedPreferencesRepositoryImpl(context: Context) : ApiSharedPreferencesRepository {

    private val sharedPrefs = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE)

    override fun saveSearchHistory(trackListHistory: MutableList<Track>) {
        val trackListHistoryJson: String = Gson().toJson(trackListHistory)
        sharedPrefs.edit()
            .putString(SEARCH_HISTORY_KEY, trackListHistoryJson)
            .apply()
    }

    override fun readSearchHistory(): Array<Track>? {
        val trackListHistoryJson: String =
            sharedPrefs.getString(SEARCH_HISTORY_KEY, null)
                ?: return emptyArray()
        return Gson().fromJson(trackListHistoryJson, Array<Track>::class.java)
    }

    override fun cleanSearchHistory() {
        sharedPrefs.edit()
            .clear()
            .apply()
    }
}