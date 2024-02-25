package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.util.Resource


interface TrackRepository {
    fun getTrack(expression: String): Resource<List<Track>>
}