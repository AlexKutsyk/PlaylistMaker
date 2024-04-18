package com.practicum.playlistmaker.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.search.domain.api.TrackHistoryInteractor
import com.practicum.playlistmaker.search.domain.api.TrackInteractor
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.presentation.models.HistoryListState
import com.practicum.playlistmaker.search.presentation.models.TrackState
import com.practicum.playlistmaker.util.debounce
import kotlinx.coroutines.launch

class SearchViewModel(
    private val trackHistorySharedPreferences: TrackHistoryInteractor,
    private val trackInteractor: TrackInteractor,
) : ViewModel() {

    private var stateSearchLiveData = MutableLiveData<TrackState>()
    fun getStateSearchLiveData(): LiveData<TrackState> = stateSearchLiveData

    private var stateListHistoryLiveData = MutableLiveData<HistoryListState>()
    fun getStateListHistoryLiveData(): LiveData<HistoryListState> = stateListHistoryLiveData

    private var trackListSearch = ArrayList<Track>()
    private var trackListHistory: MutableList<Track> = mutableListOf()

    private fun renderSearchState(stateSearching: TrackState) {
        stateSearchLiveData.postValue(stateSearching)
    }

    private var lastInputText: String? = null

    private val searchDebounceTrack = debounce<String>(
        SEARCH_DEBOUNCE_DELAY_MILLIS,
        viewModelScope,
        true
    ) { lastInputText ->
        search(lastInputText)
    }

    fun searchDebounce(input: String) {
        if (input == lastInputText) {
            return
        }
        lastInputText = input
        searchDebounceTrack(lastInputText!!)
    }

    fun search(changedText: String) {

        if (changedText.isNotEmpty()) {

            renderSearchState(TrackState.Loading())

            viewModelScope.launch {
                trackInteractor.getTrack(changedText).collect { result ->
                    trackListSearch.clear()
                    if (result.first != null) {
                        trackListSearch.addAll(result.first!!)
                    }

                    when {
                        trackListSearch.isNotEmpty() -> renderSearchState(
                            TrackState.Content(trackListSearch, true)
                        )

                        result.second == 1 -> renderSearchState(TrackState.ErrorConnect())

                        else -> {
                            renderSearchState(TrackState.Error())
                        }
                    }
                }
            }
        } else {
            renderSearchState(TrackState.Content(trackListSearch, false))
        }
    }


    fun makeHistoryList(track: Track) {
        trackListHistory.removeAll { it.trackId == track.trackId }
        trackListHistory.add(0, track)
        trackListHistory = trackListHistory.take(MAX_AMOUNT_HISTORY_ITEMS).toMutableList()
        trackHistorySharedPreferences.saveSearchHistory(trackListHistory)
    }

    fun cleanListHistory() {
        trackHistorySharedPreferences.cleanSearchHistory()
        trackListHistory =
            trackHistorySharedPreferences.readSearchHistory()!!.toMutableList()
        stateListHistoryLiveData.postValue(HistoryListState.Empty(trackListHistory))
    }

    fun showListHistory() {
        trackListHistory =
            trackHistorySharedPreferences.readSearchHistory()!!.toMutableList()
        stateListHistoryLiveData.postValue(HistoryListState.Content(trackListHistory))
    }

    fun hideListHistory() {
        stateListHistoryLiveData.postValue(HistoryListState.Invisible())
    }

    private companion object {
        const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        const val MAX_AMOUNT_HISTORY_ITEMS = 10
    }
}