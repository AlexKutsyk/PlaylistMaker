package com.practicum.playlistmaker.player.ui

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.player.ui.models.PlayStatus
import com.practicum.playlistmaker.player.ui.models.TrackScreenState
import com.practicum.playlistmaker.search.domain.models.Track

const val STEP_TO_SHOW_TIMER = 500L

class PlayerViewModel(val track: Track) : ViewModel() {

    private val mediaPlayer = MediaPlayer()

    val handler = Handler(Looper.getMainLooper())

    private var statePlayerScreenLiveData =
        MutableLiveData<TrackScreenState>(TrackScreenState.Loading)

    init {
        statePlayerScreenLiveData.postValue(TrackScreenState.Content(track))
        preparePlayer()
    }

    fun getStatePlayerScreenLiveData(): LiveData<TrackScreenState> = statePlayerScreenLiveData

    private var playStatusLiveData = MutableLiveData<PlayStatus>()
    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData

    private fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnPreparedListener {
            playStatusLiveData.postValue(PlayStatus.Loading)
        }

        mediaPlayer.setOnCompletionListener {
            playStatusLiveData.postValue(PlayStatus.Finish)
            handler.removeCallbacks(currentTimeTrack)
        }
    }

    fun startPlayer() {
        mediaPlayer.start()
        getProgressTrack()
    }

    fun pausePlayer() {
        mediaPlayer.pause()
        handler.removeCallbacks(currentTimeTrack)
        playStatusLiveData.postValue(PlayStatus.Pause)
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }

    private fun getProgressTrack() {
        handler.post(currentTimeTrack)
    }

    private val currentTimeTrack = object : Runnable {
        override fun run() {
            playStatusLiveData.postValue(PlayStatus.Play(mediaPlayer.currentPosition.toLong()))
            handler.postDelayed(this, STEP_TO_SHOW_TIMER)
        }
    }

    companion object {
        fun getViewModelFactory(track: Track): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayerViewModel(
                    track
                )
            }
        }
    }
}

