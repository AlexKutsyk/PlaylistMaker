package com.practicum.playlistmaker.ui.player

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.Creator
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.models.Track
import com.practicum.playlistmaker.ui.search.dpToPx
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {

    //    val mediaPlayer = MediaPlayer()
    val mediaPlayer = Creator.provideMediaPlayerInteractor()
    val handler = Handler(Looper.getMainLooper())
    private var urlTrack: String? = null
    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private lateinit var timePlaying: TextView
    private val timeTrack: SimpleDateFormat by lazy {
        SimpleDateFormat(
            getString(R.string.time_track_mm_ss),
            Locale.getDefault()
        )
    }
    private val yearTrack: SimpleDateFormat by lazy {
        SimpleDateFormat(
            getString(R.string.year_track_yyyy),
            Locale.getDefault()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val backButton: ImageButton = findViewById(R.id.backButton)
        val trackName: TextView = findViewById(R.id.nameTrack)
        val artistName: TextView = findViewById(R.id.nameArtist)
        val duration: TextView = findViewById(R.id.durationValue)
        val coverAlbum: ImageView = findViewById(R.id.coverAlbum)
        val collectionName: TextView = findViewById(R.id.albumValue)
        val collectionGroup: Group = findViewById(R.id.albumGroup)
        val releaseDate: TextView = findViewById(R.id.yearValue)
        val genreName: TextView = findViewById(R.id.genreValue)
        val country: TextView = findViewById(R.id.countryValue)
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        timePlaying = findViewById(R.id.timePlaying)

        playButton.isEnabled = false
        backButton.setOnClickListener { finish() }

        val track: Track = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KEY_TRACK, Track::class.java)!!
        } else {
            intent.getParcelableExtra(KEY_TRACK)!!
        }

        urlTrack = track.previewUrl
        trackName.text = track.trackName
        artistName.text = track.artistName
        duration.text = timeTrack.format(track.trackTimeMillis)

        Glide.with(coverAlbum)
            .load(track.getCoverArtworkUrl())
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(dpToPx(8f, coverAlbum.context)))
            .into(coverAlbum)

        if (track.collectionName.isNullOrEmpty()) {
            collectionGroup.visibility = View.GONE
        } else {
            collectionName.text = track.collectionName
        }

        releaseDate.text = yearTrack.format(
            SimpleDateFormat(getString(R.string.year_track_yyyy)).parse(track.releaseDate)
        )

        genreName.text = track.primaryGenreName
        country.text = track.country
        timePlaying.text = timeTrack.format(START_TIME)

        preparePlayer()

        playButton.setOnClickListener {
            startPlayer()
        }

        pauseButton.setOnClickListener {
            pausePlayer()
        }
    }

    private fun preparePlayer() {
        if (urlTrack.isNullOrEmpty()) {
            timePlaying.text = getString(R.string.no_demo)
        } else {
            mediaPlayer.setDataSource(urlTrack)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener(param = {
                playButton.visibility = View.VISIBLE
                pauseButton.visibility = View.GONE
                playButton.isEnabled = true
            }
            )
            mediaPlayer.setOnCompletionListener(param = {
                playButton.visibility = View.VISIBLE
                pauseButton.visibility = View.GONE
                timePlaying.text = timeTrack.format(START_TIME)
                handler.removeCallbacks(currentTimeTrack)
            })
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playButton.visibility = View.GONE
        pauseButton.visibility = View.VISIBLE
        timer()
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playButton.visibility = View.VISIBLE
        pauseButton.visibility = View.GONE
        handler.removeCallbacks(currentTimeTrack)
    }

    private fun timer() {
        handler.post(currentTimeTrack)
    }

    private var currentTimeTrack = object : Runnable {
        override fun run() {
            timePlaying.text = timeTrack.format(mediaPlayer.currentPosition().toLong())
            handler.postDelayed(this, 500)
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
        handler.removeCallbacks(currentTimeTrack)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(currentTimeTrack)
        mediaPlayer.release()
    }

}

const val KEY_TRACK = "track"
const val START_TIME = 0
