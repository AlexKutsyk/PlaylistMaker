package com.practicum.playlistmaker.search.domain.impl

import com.practicum.playlistmaker.search.domain.api.ApiTrackInteractor
import com.practicum.playlistmaker.search.domain.api.ApiTrackRepository
import com.practicum.playlistmaker.util.Resource


class TrackInteractorImpl(private val repository: ApiTrackRepository) : ApiTrackInteractor {

    override fun getTrack(
        expression: String,
        consumer: ApiTrackInteractor.TrackConsumer
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