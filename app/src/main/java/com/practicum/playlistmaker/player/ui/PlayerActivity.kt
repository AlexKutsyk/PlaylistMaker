package com.practicum.playlistmaker.player.ui

import android.os.Build.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.player.presentation.PlayerViewModel
import com.practicum.playlistmaker.player.presentation.models.PlayStatus
import com.practicum.playlistmaker.player.presentation.models.TrackScreenState
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.ui.dpToPx
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {

    private lateinit var track: Track

    private val viewModel: PlayerViewModel by viewModel {
        parametersOf(track)
    }

    private lateinit var binding: ActivityPlayerBinding

    private val formatTimeTrack: SimpleDateFormat by lazy {
        SimpleDateFormat(
            getString(R.string.time_track_mm_ss),
            Locale.getDefault()
        )
    }
    private val formatYearTrack: SimpleDateFormat by lazy {
        SimpleDateFormat(
            getString(R.string.year_track_yyyy),
            Locale.getDefault()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.isEnabled = false
        binding.backButton.setOnClickListener { finish() }

        track = if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KEY_TRACK, Track::class.java)!!
        } else {
            intent.getParcelableExtra(KEY_TRACK)!!
        }

        binding.playButton.setOnClickListener {
            viewModel.startPlayer()
        }

        binding.pauseButton.setOnClickListener {
            viewModel.pausePlayer()
        }

        viewModel.getStatePlayerScreenLiveData().observe(this) {

            when (it) {
                is TrackScreenState.Loading -> {
                    changeScreenPlayer(true)
                }

                is TrackScreenState.Content -> {
                    changeScreenPlayer(false)
                    showValueTrack(it.track)
                    showCowerAlbum(it.track)
                    checkAndShowAlbum(it.track)
                }

                is TrackScreenState.NoDemo -> {
                    changeScreenPlayer(false)
                    showValueTrack(it.track)
                    showCowerAlbum(it.track)
                    checkAndShowAlbum(it.track)
                    showScreenPlayerWODemo()
                }
            }
        }

        viewModel.getPlayStatusLiveData().observe(this) {

            when (it) {
                is PlayStatus.Loading -> {
                    binding.apply {
                        playButton.isVisible = true
                        pauseButton.isVisible = false
                        playButton.isEnabled = true
                    }
                }

                is PlayStatus.Play -> {
                    binding.apply {
                        playButton.isVisible = false
                        pauseButton.isVisible = true
                        timePlaying.text = formatTimeTrack.format(it.progress)
                    }
                }

                is PlayStatus.Pause -> {
                    binding.apply {
                        playButton.isVisible = true
                        playButton.isEnabled = true
                        pauseButton.isVisible = false
                    }
                }

                is PlayStatus.Finish -> {
                    binding.apply {
                        playButton.isVisible = true
                        pauseButton.isVisible = false
                        timePlaying.text = formatTimeTrack.format(START_TIME)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }

    private fun checkAndShowAlbum(track: Track) {
        if (track.collectionName.isNullOrEmpty()) {
            binding.albumGroup.visibility = View.GONE
        } else {
            binding.albumValue.text = track.collectionName
        }
    }

    private fun showCowerAlbum(track: Track) {
        Glide.with(binding.coverAlbum)
            .load(track.getCoverArtworkUrl())
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(dpToPx(8f, binding.coverAlbum.context)))
            .into(binding.coverAlbum)
    }

    private fun changeScreenPlayer(isShow: Boolean) {
        if (isShow) {
            binding.apply {
                progressBar.isVisible = true

                coverAlbum.isVisible = false
                nameTrack.isVisible = false
                nameArtist.isVisible = false
                durationValue.isVisible = false
                albumValue.isVisible = false
                yearValue.isVisible = false
                genreValue.isVisible = false
                countryValue.isVisible = false
            }
        } else {
            binding.apply {
                progressBar.isVisible = false

                coverAlbum.isVisible = true
                nameTrack.isVisible = true
                nameArtist.isVisible = true
                durationValue.isVisible = true
                albumValue.isVisible = true
                yearValue.isVisible = true
                genreValue.isVisible = true
                countryValue.isVisible = true
            }
        }
    }

    private fun showValueTrack(track: Track) {
        binding.apply {
            with(track) {
                nameTrack.text = trackName
                nameArtist.text = artistName
                durationValue.text = formatTimeTrack.format(track.trackTimeMillis)
                genreValue.text = primaryGenreName
                countryValue.text = country
                timePlaying.text = formatTimeTrack.format(START_TIME)
                yearValue.text = formatYearTrack.format(
                    SimpleDateFormat(getString(R.string.year_track_yyyy)).parse(track.releaseDate)
                )
            }
        }
    }

    private fun showScreenPlayerWODemo() {
        binding.timePlaying.text = "No Demo"
    }

    private companion object {
        const val START_TIME = 0
    }
}

const val KEY_TRACK = "track"
