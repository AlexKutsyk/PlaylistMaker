package com.practicum.playlistmaker.search.data

import android.content.Context
import com.practicum.playlistmaker.library.favorites.data.db.AppDataBase
import com.practicum.playlistmaker.search.data.dto.TrackSearchRequest
import com.practicum.playlistmaker.search.data.dto.TracksSearchResponse
import com.practicum.playlistmaker.search.domain.api.TrackRepository
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class TrackRepositoryImpl(
    private val networkClient: NetworkClient,
    val context: Context,
    private val appDataBase: AppDataBase,
) :
    TrackRepository {

    override fun getTrack(expression: String): Flow<Resource<List<Track>>> = flow {

        val response = networkClient.doRequest(TrackSearchRequest(expression))

        when (response.resultCode) {

            -1 -> emit(Resource.Error(ERROR_CONNECT))

            200 -> {
                if ((response as TracksSearchResponse).results.isEmpty()) {
                    emit(Resource.Error(ERROR_EMPTY))
                } else {

                    val tracksIdFavorites = appDataBase.trackDao().getIdFavoritesTrack()

                    val result = Resource.Success(
                        response.results.map {
                            Track(
                                0,
                                0,
                                it.trackName,
                                it.artistName,
                                it.trackTimeMillis?: 0,
                                it.artworkUrl100,
                                it.trackId,
                                it.collectionName,
                                it.releaseDate,
                                it.primaryGenreName,
                                it.country,
                                it.previewUrl,
                            )
                        })

                    checkIsFavorite(tracksIdFavorites, result)

                    emit(result)
                }
            }

            else -> emit(Resource.Error(ERROR_SERVER))
        }
    }

    private fun checkIsFavorite(
        tracksIdFavorites: List<Int>,
        result: Resource<List<Track>>
    ) {
        tracksIdFavorites.forEach { idFavorites ->
            result.data?.forEach { track ->
                if (idFavorites == track.trackId) {
                    track.isFavorite = true
                }
            }
        }
    }

    companion object {
        const val ERROR_EMPTY = 0
        const val ERROR_CONNECT = 1
        const val ERROR_SERVER = 2
    }
}

