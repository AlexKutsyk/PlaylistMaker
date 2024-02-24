package com.practicum.playlistmaker.search.data

import android.content.Context
import com.practicum.playlistmaker.search.data.dto.TrackSearchRequest
import com.practicum.playlistmaker.search.data.dto.TracksSearchResponse
import com.practicum.playlistmaker.search.domain.api.ApiTrackRepository
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.util.Resource


class TrackRepositoryImpl(
    private val networkClient: NetworkClient,
    val context: Context) :
    ApiTrackRepository {

    override fun getTrack(expression: String): Resource<List<Track>> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))

        return when (response.resultCode) {

            -1 -> Resource.Error(ERROR_CONNECT)

            200 -> {
                if ((response as TracksSearchResponse).results.isEmpty()) {
                    Resource.Error(ERROR_EMPTY)
                } else {
                    Resource.Success(
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
                }
            }

            else -> Resource.Error(ERROR_SERVER)
        }
    }

    companion object {
        const val ERROR_EMPTY = 0
        const val ERROR_CONNECT = 1
        const val ERROR_SERVER = 2
    }
}

