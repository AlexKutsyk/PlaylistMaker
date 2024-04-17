package com.practicum.playlistmaker.search.data

import android.content.Context
import com.practicum.playlistmaker.search.data.dto.TrackSearchRequest
import com.practicum.playlistmaker.search.data.dto.TracksSearchResponse
import com.practicum.playlistmaker.search.domain.api.TrackRepository
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class TrackRepositoryImpl(
    private val networkClient: NetworkClient,
    val context: Context) :
    TrackRepository {

    override fun getTrack(expression: String): Flow<Resource<List<Track>>> = flow {

        val response = networkClient.doRequest(TrackSearchRequest(expression))

        when (response.resultCode) {

            -1 -> emit(Resource.Error(ERROR_CONNECT))

            200 -> {
                if ((response as TracksSearchResponse).results.isEmpty()) {
                    emit(Resource.Error(ERROR_EMPTY))
                } else {
                    val result = Resource.Success(
                        response.results.map {
                            Track(
                                it.trackName,
                                it.artistName,
                                it.trackTimeMillis,
                                it.artworkUrl100,
                                it.trackId,
                                it.collectionName,
                                it.releaseDate,
                                it.primaryGenreName,
                                it.country,
                                it.previewUrl
                            )
                        })
                    emit(result)
                }
            }

            else -> emit(Resource.Error(ERROR_SERVER))
        }
    }

    companion object {
        const val ERROR_EMPTY = 0
        const val ERROR_CONNECT = 1
        const val ERROR_SERVER = 2
    }
}

