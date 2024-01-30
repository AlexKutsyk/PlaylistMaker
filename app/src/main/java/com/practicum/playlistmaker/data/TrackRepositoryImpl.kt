package com.practicum.playlistmaker.data

import com.practicum.playlistmaker.data.dto.TracksSearchResponse
import com.practicum.playlistmaker.domain.api.ApiTrackRepository
import com.practicum.playlistmaker.domain.models.Track

class TrackRepositoryImpl(private val networkClient: NetworkClient) : ApiTrackRepository {
    override fun getTrack(expression: String): List<Track> {
        val response = networkClient.doRequest(expression)
        if (response.resultCode == 200) {
            return (response as TracksSearchResponse).results.map {
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
            }
        } else {
            return emptyList()
        }
    }
}