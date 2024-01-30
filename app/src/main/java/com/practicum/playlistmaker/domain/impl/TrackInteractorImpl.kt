package com.practicum.playlistmaker.domain.impl

import com.practicum.playlistmaker.domain.api.ApiTrackInteractor
import com.practicum.playlistmaker.domain.api.ApiTrackRepository

class TrackInteractorImpl(private val repository: ApiTrackRepository) : ApiTrackInteractor {
    override fun getTrack(
        expression: String,
        consumer: ApiTrackInteractor.TrackConsumer
    ) {
        val thread = Thread {
    consumer.consume(repository.getTrack(expression))
        }
        thread.start()
    }

}