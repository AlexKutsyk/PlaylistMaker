package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.data.dto.TrackSearchRequest
import com.practicum.playlistmaker.domain.models.Track

interface TrackRepository {
    fun getTrack(trackSearchRequest: TrackSearchRequest): List<Track>
}