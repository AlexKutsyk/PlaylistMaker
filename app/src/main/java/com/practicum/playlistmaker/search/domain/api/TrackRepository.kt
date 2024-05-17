package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun getTrack(expression: String): Flow<Resource<List<Track>>>
}