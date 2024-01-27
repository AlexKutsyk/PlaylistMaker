package com.practicum.playlistmaker.data

import android.content.Intent
import android.os.Build
import com.practicum.playlistmaker.domain.api.TrackIntentRepository
import com.practicum.playlistmaker.domain.models.Track
import com.practicum.playlistmaker.ui.player.KEY_TRACK

class TrackIntentRepositoryImpl(private val intent: Intent) : TrackIntentRepository {
    override fun getTrack(): Track {
        val track: Track = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KEY_TRACK, Track::class.java)!!
        } else {
            intent.getParcelableExtra(KEY_TRACK)!!
        }
    return track
    }
}