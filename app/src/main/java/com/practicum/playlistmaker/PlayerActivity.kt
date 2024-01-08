package com.practicum.playlistmaker

import android.media.MediaPlayer
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
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {

    private var mediaPlayer = MediaPlayer()
    private var urlTrack: String? = null
    var durationTimeTrack: Long = 0
    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton
    val handler = Handler(Looper.getMainLooper())
    lateinit var timePlaying: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        playButton = findViewById(R.id.playButton)
        playButton.isEnabled = false
        pauseButton = findViewById(R.id.pauseButton)
        urlTrack = intent.getStringExtra(KEY_PREVIEWURL)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val trackName: TextView = findViewById(R.id.nameTrack)
        trackName.text = intent.getStringExtra(KEY_TRACKNAME)

        val artistName: TextView = findViewById(R.id.nameArtist)
        artistName.text = intent.getStringExtra(KEY_ARTISTNAME)

        val duration: TextView = findViewById(R.id.durationValue)
        duration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(
            intent.getIntExtra(KEY_TRACKTIMEMILLIS, 0)
        )

        val coverAlbum: ImageView = findViewById(R.id.coverAlbum)
        Glide.with(coverAlbum)
            .load(
                intent.getStringExtra(KEY_ARTWORKURL100)
                    ?.replaceAfterLast('/', "512x512bb.jpg")
            )
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(dpToPx(8f, coverAlbum.context)))
            .into(coverAlbum)

        val collectionName: TextView = findViewById(R.id.albumValue)
        val collectionGroup: Group = findViewById(R.id.albumGroup)
        if (intent.getStringExtra(KEY_COLLECTIONNAME).isNullOrEmpty()) {
            collectionGroup.visibility = View.GONE
        } else {
            collectionName.text = intent.getStringExtra(KEY_COLLECTIONNAME)
        }

        val releaseDate: TextView = findViewById(R.id.yearValue)
        releaseDate.text = SimpleDateFormat("yyyy", Locale.getDefault()).format(
            SimpleDateFormat("yyyy").parse(intent.getStringExtra(KEY_RELEASEDATE))
        )

        val genreName: TextView = findViewById(R.id.genreValue)
        genreName.text = intent.getStringExtra(KEY_GENRENAME)

        val country: TextView = findViewById(R.id.countryValue)
        country.text = intent.getStringExtra(KEY_COUNTRY)

        timePlaying = findViewById(R.id.timePlaying)
        timePlaying.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(durationTimeTrack)

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
            timePlaying.text = "No Demo"
        } else {
            mediaPlayer.setDataSource(urlTrack)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                playButton.visibility = View.VISIBLE
                pauseButton.visibility = View.GONE
                playButton.isEnabled = true
            }
            mediaPlayer.setOnCompletionListener {
                playButton.visibility = View.VISIBLE
                pauseButton.visibility = View.GONE
                durationTimeTrack = 0
                timePlaying.text =
                    SimpleDateFormat("mm:ss", Locale.getDefault()).format(durationTimeTrack)
                handler.removeCallbacks(currentTimeTrack)
            }
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
        val thread = Thread {
            handler.post(currentTimeTrack)
        }
        thread.start()
    }

    private var currentTimeTrack = object : Runnable {
        override fun run() {
            durationTimeTrack = mediaPlayer.currentPosition.toLong()
            timePlaying.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(durationTimeTrack)
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

const val KEY_TRACKNAME = "trackName"
const val KEY_ARTISTNAME = "artistName"
const val KEY_TRACKTIMEMILLIS = "trackTimeMillis"
const val KEY_ARTWORKURL100 = "artworkUrl100"
const val KEY_COLLECTIONNAME = "collectionName"
const val KEY_RELEASEDATE = "releaseDate"
const val KEY_GENRENAME = "genreName"
const val KEY_COUNTRY = "country"
const val KEY_PREVIEWURL = "previewUrl"
