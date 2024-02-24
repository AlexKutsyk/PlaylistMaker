package com.practicum.playlistmaker.search.ui

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.search.domain.api.ApiSharedPreferencesHistorySearchInteractor
import com.practicum.playlistmaker.search.domain.api.ApiTrackInteractor
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.ui.models.HistoryListState
import com.practicum.playlistmaker.search.ui.models.TrackState

class SearchViewModel(
    private val trackHistorySharedPreferences: ApiSharedPreferencesHistorySearchInteractor,
    private val trackInteractor: ApiTrackInteractor,
) : ViewModel() {

    private var stateSearchLiveData = MutableLiveData<TrackState>()
    fun getStateSearchLiveData(): LiveData<TrackState> = stateSearchLiveData

    private var stateListHistoryLiveData = MutableLiveData<HistoryListState>()
    fun getStateListHistoryLiveData(): LiveData<HistoryListState> = stateListHistoryLiveData


    private var trackListSearch = ArrayList<Track>()
    private var trackListHistory: MutableList<Track> = mutableListOf()

    private val handler = Handler(Looper.getMainLooper())

    private fun renderSearchState(stateSearching: TrackState) {
        stateSearchLiveData.postValue(stateSearching)
    }

    fun search(changedText: String) {
        if (changedText.isNotEmpty()) {

            renderSearchState(TrackState.Loading)

            trackInteractor.getTrack(changedText, object : ApiTrackInteractor.TrackConsumer {
                override fun consume(
                    foundsTracks: List<Track>?,
                    typeError: Int?
                ) {
                    trackListSearch.clear()
                    if (foundsTracks != null) {
                        trackListSearch.addAll(foundsTracks)
                    }

                    when {
                        trackListSearch.isNotEmpty() -> renderSearchState(
                            TrackState.Content(trackListSearch)
                        )

                        typeError == 1 -> renderSearchState(TrackState.ErrorConnect)

                        else -> {
                            renderSearchState(TrackState.Error)
                        }
                    }
                }
            })
        }
    }

    fun searchDebounce(changedText: String) {

        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { search(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY

        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime
        )
    }

    fun makeHistoryList(track: Track) {
        trackListHistory.removeAll { it.trackId == track.trackId }
        trackListHistory.add(0, track)
        trackListHistory = trackListHistory.take(MAX_AMOUNT_HISTORY_ITEMS).toMutableList()
        trackHistorySharedPreferences.saveSearchHistory(trackListHistory)
    }

    fun cleanListHistory() {
        stateListHistoryLiveData.postValue(HistoryListState.Empty(trackListHistory))
        trackHistorySharedPreferences.cleanSearchHistory()
        trackListHistory =
            trackHistorySharedPreferences.readSearchHistory()!!.toMutableList()
    }

    fun showListHistory() {
        trackListHistory =
            trackHistorySharedPreferences.readSearchHistory()!!.toMutableList()
        stateListHistoryLiveData.postValue(HistoryListState.Content(trackListHistory))
    }

    companion object {

        const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val MAX_AMOUNT_HISTORY_ITEMS = 10
        val SEARCH_REQUEST_TOKEN = Any()

    }
}