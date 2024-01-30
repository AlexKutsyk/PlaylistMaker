package com.practicum.playlistmaker

import android.content.Context
import android.media.MediaPlayer
import com.practicum.playlistmaker.data.TrackRepositoryImpl
import com.practicum.playlistmaker.data.media_player.MediaPlayerRepositoryImpl
import com.practicum.playlistmaker.data.shared_preferences.SharedPreferencesRepositoryImpl
import com.practicum.playlistmaker.domain.api.ApiMediaPlayerInteractor
import com.practicum.playlistmaker.domain.api.ApiSharedPreferencesInteractor
import com.practicum.playlistmaker.domain.api.ApiSharedPreferencesRepository
import com.practicum.playlistmaker.domain.api.ApiMediaPlayerRepository
import com.practicum.playlistmaker.domain.api.ApiTrackInteractor
import com.practicum.playlistmaker.domain.api.ApiTrackRepository
import com.practicum.playlistmaker.domain.impl.MediaPlayerInteractorImpl
import com.practicum.playlistmaker.domain.impl.SharedPreferencesInteractorImpl
import com.practicum.playlistmaker.domain.impl.TrackInteractorImpl

object Creator {
//    private fun provideTrackRepository(): ApiTrackRepository {
//        return TrackRepositoryImpl(RetrofitNetworkClient())
//    }
//    fun provideTrackInteractor(): ApiTrackInteractor {
//        return TrackInteractorImpl(provideTrackRepository())
//    }

    fun provideMediaPlayerInteractor(): ApiMediaPlayerInteractor {
        return MediaPlayerInteractorImpl(provideMediaPlayerRepository())
    }

    private fun provideMediaPlayerRepository(): ApiMediaPlayerRepository {
        return MediaPlayerRepositoryImpl(MediaPlayer())
    }

    private fun provideSharedPreferencesRepository(context: Context) : ApiSharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(context)
    }
    fun provideSharedPreferencesInteractor(context: Context) : ApiSharedPreferencesInteractor {
        return SharedPreferencesInteractorImpl(provideSharedPreferencesRepository(context))
    }
}