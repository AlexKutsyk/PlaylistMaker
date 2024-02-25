package com.practicum.playlistmaker.search.domain.impl

import com.practicum.playlistmaker.search.domain.api.TrackInteractor
import com.practicum.playlistmaker.search.domain.api.TrackRepository
import com.practicum.playlistmaker.util.Resource


class TrackInteractorImpl(
    private val repository: TrackRepository
) : TrackInteractor {

    override fun getTrack(
        expression: String,
        consumer: TrackInteractor.TrackConsumer
    ) {
        val thread = Thread {

            when (val resource = repository.getTrack(expression)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(null, resource.typeError)
                }
            }
        }
        thread.start()
    }

}