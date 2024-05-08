package com.practicum.playlistmaker.library.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.library.domain.db.FavoritesInteractor
import com.practicum.playlistmaker.library.presentation.models.FavoriteState
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesInteractor: FavoritesInteractor,
) : ViewModel() {

    private var stateFavoritesTracks = MutableLiveData<FavoriteState>()
    fun getStateFavoritesTracks(): LiveData<FavoriteState> = stateFavoritesTracks

    init {
        getFavoritesTracks()
    }

    fun getFavoritesTracks() {
        viewModelScope.launch {
            favoritesInteractor.getAllFavoritesTracks().collect{
                if (it.isEmpty()) {
                    stateFavoritesTracks.postValue(FavoriteState.Empty())
                } else {
                    val reverseListFavotitesTracks = it.reversed()
                    stateFavoritesTracks.postValue(FavoriteState.Content(reverseListFavotitesTracks))
                }
            }
        }
    }

}