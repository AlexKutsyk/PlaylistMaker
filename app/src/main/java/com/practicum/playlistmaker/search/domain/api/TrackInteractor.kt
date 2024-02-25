package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.models.Track

interface TrackInteractor {
    fun getTrack(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(
            foundsTracks: List<Track>?,
            typeError: Int?
        )
    }

}
