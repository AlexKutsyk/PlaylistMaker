package com.practicum.playlistmaker.search.domain.impl

import com.practicum.playlistmaker.search.domain.api.TrackInteractor
import com.practicum.playlistmaker.search.domain.api.TrackRepository
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class TrackInteractorImpl(
    private val repository: TrackRepository
) : TrackInteractor {

    override fun getTrack(expression: String): Flow<Pair<List<Track>?, Int?>> {

        return repository.getTrack(expression).map { result ->

            when (result) {

                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.typeError)
                }
            }
        }
    }

}