package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.Track

interface ApiTrackRepository {
    fun getTrack(expression: String): List<Track>
}