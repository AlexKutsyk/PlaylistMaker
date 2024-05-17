package com.practicum.playlistmaker.library.favorites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.library.favorites.domain.db.FavoritesInteractor
import com.practicum.playlistmaker.library.favorites.presentation.models.FavoriteState
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesInteractor: FavoritesInteractor,
) : ViewModel() {

    private var stateFavoritesTracks = MutableLiveData<FavoriteState>()
    fun getStateFavoritesTracks(): LiveData<FavoriteState> = stateFavoritesTracks

    fun getFavoritesTracks() {
        viewModelScope.launch {
            favoritesInteractor.getAllFavoritesTracks().collect { favoriteListTracks ->
                if (favoriteListTracks.isEmpty()) {
                    stateFavoritesTracks.postValue(FavoriteState.Empty())
                } else {
                    val reverseListFavotitesTracks = favoriteListTracks.reversed()
                    stateFavoritesTracks.postValue(FavoriteState.Content(reverseListFavotitesTracks))
                }
            }
        }
    }

}