package com.practicum.playlistmaker.player.presentation

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.library.domain.db.FavoritesInteractor
import com.practicum.playlistmaker.player.presentation.models.FavoriteState
import com.practicum.playlistmaker.player.presentation.models.PlayerStatus
import com.practicum.playlistmaker.player.presentation.models.PlayerScreenState
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerViewModel(
    val track: Track,
    val context: Context,
    private val favoritesInteractor: FavoritesInteractor,
) : ViewModel() {

    private val mediaPlayer = MediaPlayer()

    private var currentTimeTrack: Job? = null

    private var statePlayerScreenLiveData =
        MutableLiveData<PlayerScreenState>(PlayerScreenState.Loading)

    private var favoriteState = MutableLiveData<FavoriteState>()

    init {
        checkIsTrackFavorite()
        checkUrlTrack()
        statePlayerScreenLiveData.postValue(PlayerScreenState.Content(track))
    }

    private fun checkUrlTrack() {
        if (track.previewUrl.isNullOrEmpty()) {
            statePlayerScreenLiveData.postValue(PlayerScreenState.NoDemo(track))
        } else {
            preparePlayer()
        }
    }

    fun getFavoriteState(): LiveData<FavoriteState> = favoriteState

    fun getStatePlayerScreenLiveData(): LiveData<PlayerScreenState> = statePlayerScreenLiveData

    private var playerStatusLiveData = MutableLiveData<PlayerStatus>()
    fun getPlayStatusLiveData(): LiveData<PlayerStatus> = playerStatusLiveData

    private fun preparePlayer() {
        if (!track.previewUrl.isNullOrEmpty()) mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnPreparedListener {
            playerStatusLiveData.postValue(PlayerStatus.Prepared())
        }

        mediaPlayer.setOnCompletionListener {
            playerStatusLiveData.postValue(PlayerStatus.Finished())
            currentTimeTrack?.cancel()
        }
    }

    fun startPlayer() {
        mediaPlayer.start()
        startTimer()
    }

    fun pausePlayer() {
        mediaPlayer.pause()
        currentTimeTrack?.cancel()
        playerStatusLiveData.postValue(PlayerStatus.Pause(getCurrentPositionTrack()))
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
        currentTimeTrack = null
    }

    private fun startTimer() {
        currentTimeTrack =
            viewModelScope.launch {
                while (mediaPlayer.isPlaying) {
                    playerStatusLiveData.postValue(PlayerStatus.Playing(getCurrentPositionTrack()))
                    delay(STEP_TO_SHOW_TIMER_MILLIS)
                }
            }
    }

    private fun getCurrentPositionTrack(): String {
        return SimpleDateFormat(
            context.getString(R.string.time_track_mm_ss),
            Locale.getDefault()
        ).format(mediaPlayer.currentPosition) ?: context.getString(R.string.time_track_mm_ss)
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            if (track.isFavorite) {
                favoritesInteractor.deleteTrack(track)
                track.isFavorite = false

            } else {
                track.isFavorite = true
                favoritesInteractor.insertTrack(track)
            }
            favoriteState.postValue(FavoriteState(track.isFavorite))
        }
    }

    private fun checkIsTrackFavorite() {
        viewModelScope.launch {
            favoritesInteractor.getAllFavoritesTracks().collect { listFavoritesTracks ->
                listFavoritesTracks.forEach { favoritTrack ->
                    if (track.trackId == favoritTrack.trackId) {
                        track.isFavorite = true
                        track.id = favoritTrack.id
                    }
                }
            }
            favoriteState.postValue(FavoriteState(track.isFavorite))
        }
    }

    private companion object {
        const val STEP_TO_SHOW_TIMER_MILLIS = 300L
    }
}

