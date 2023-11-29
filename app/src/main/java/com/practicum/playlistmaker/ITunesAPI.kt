package com.practicum.playlistmaker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesAPI {
@GET ("/search")
fun findTrack (@Query("term") text: String) : Call<TracksResponse> }