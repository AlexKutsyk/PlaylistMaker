package com.practicum.playlistmaker.domain.impl

import com.practicum.playlistmaker.domain.api.ApiGetTrackUseCase
import com.practicum.playlistmaker.domain.api.TrackIntentRepository
import com.practicum.playlistmaker.domain.models.Track

class GetTrackUseCaseImpl(private val trackIntentRepository: TrackIntentRepository) : ApiGetTrackUseCase{
    override fun execute(): Track {
        return trackIntentRepository.getTrack()
    }
}