package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.data.dto.TrackSearchRequest
import com.practicum.playlistmaker.domain.models.Track

interface TrackInteractor {
    fun getTrack(trackSearchRequest: TrackSearchRequest, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(tracks: List<Track>)
    }
}
