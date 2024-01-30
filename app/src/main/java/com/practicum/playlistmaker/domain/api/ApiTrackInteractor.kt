package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.Track

interface ApiTrackInteractor {
    fun getTrack(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(tracks: List<Track>)
    }

}
