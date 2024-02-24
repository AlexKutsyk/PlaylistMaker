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
import com.practicum.playlistmaker.player.ui.models.PlayStatus
import com.practicum.playlistmaker.player.ui.models.TrackScreenState
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.ui.dpToPx
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {

    lateinit var track: Track

    private val viewModel: PlayerViewModel by viewModel {
        parametersOf(track)
    }

    private lateinit var binding: ActivityPlayerBinding

    private var urlTrack: String? = null

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

        urlTrack = track.previewUrl

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
                    binding.nameTrack.text = track.trackName
                    binding.nameArtist.text = track.artistName
                    binding.durationValue.text = formatTimeTrack.format(track.trackTimeMillis)
                    showCowerAlbum(track)
                    checkAndShowAlbum(track)
                    binding.yearValue.text = formatYearTrack.format(
                        SimpleDateFormat(getString(R.string.year_track_yyyy)).parse(track.releaseDate)
                    )
                    binding.genreValue.text = track.primaryGenreName
                    binding.countryValue.text = track.country
                    binding.timePlaying.text = formatTimeTrack.format(START_TIME)
                }
            }
        }

        viewModel.getPlayStatusLiveData().observe(this) {

            when (it) {
                is PlayStatus.Loading -> {
                    binding.playButton.isVisible = true
                    binding.pauseButton.isVisible = false
                    binding.playButton.isEnabled = true

                }

                is PlayStatus.Play -> {
                    binding.playButton.isVisible = false
                    binding.pauseButton.isVisible = true
                    binding.timePlaying.text = formatTimeTrack.format(it.progress)

                }

                is PlayStatus.Pause -> {
                    binding.playButton.isVisible = true
                    binding.playButton.isEnabled = true
                    binding.pauseButton.isVisible = false

                }

                is PlayStatus.Finish -> {
                    binding.playButton.isVisible = true
                    binding.pauseButton.isVisible = false
                    binding.timePlaying.text = formatTimeTrack.format(START_TIME)
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
            binding.progressBar.isVisible = true

            binding.coverAlbum.isVisible = false
            binding.nameTrack.isVisible = false
            binding.nameArtist.isVisible = false
            binding.durationValue.isVisible = false
            binding.albumValue.isVisible = false
            binding.yearValue.isVisible = false
            binding.genreValue.isVisible = false
            binding.countryValue.isVisible = false
        } else {
            binding.progressBar.isVisible = false

            binding.coverAlbum.isVisible = true
            binding.nameTrack.isVisible = true
            binding.nameArtist.isVisible = true
            binding.durationValue.isVisible = true
            binding.albumValue.isVisible = true
            binding.yearValue.isVisible = true
            binding.genreValue.isVisible = true
            binding.countryValue.isVisible = true
        }
    }

}

const val KEY_TRACK = "track"
const val START_TIME = 0