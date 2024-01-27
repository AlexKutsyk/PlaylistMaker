package com.practicum.playlistmaker.domain.impl

import com.practicum.playlistmaker.data.dto.TrackSearchRequest
import com.practicum.playlistmaker.domain.api.TrackInteractor
import com.practicum.playlistmaker.domain.api.TrackRepository

class TrackInteractorImpl(private val repository: TrackRepository) : TrackInteractor {
    override fun getTrack(
        trackSearchRequest: TrackSearchRequest,
        consumer: TrackInteractor.TrackConsumer
    ) {
        val thread = Thread {
    consumer.consume(repository.getTrack(trackSearchRequest))
        }
        thread.start()
    }
}