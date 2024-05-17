package com.practicum.playlistmaker.library.favorites.presentation.models

import com.practicum.playlistmaker.search.domain.models.Track

sealed class FavoriteState(
    val listFavoritesTracks: List<Track>?,
    val isVisibleFavorites: Boolean,
    val isVisiblePlaseholder: Boolean
) {
    class Content(listFavoritesTracks: List<Track>) : FavoriteState(
        listFavoritesTracks,
        true,
        false
    )

    class Empty : FavoriteState(
        null,
        false,
        true
    )
}