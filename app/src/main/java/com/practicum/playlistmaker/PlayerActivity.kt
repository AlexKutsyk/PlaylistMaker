package com.practicum.playlistmaker

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }


        val trackName: TextView = findViewById(R.id.nameTrack)
        trackName.text = intent.getStringExtra("trackName")

        val artistName: TextView = findViewById(R.id.nameArtist)
        artistName.text = intent.getStringExtra("artistName")

        val duration: TextView = findViewById(R.id.durationValue)
        duration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(
            intent.getIntExtra("trackTimeMillis", 0)
        )

        val coverAlbum: ImageView = findViewById(R.id.coverAlbum)
        Glide.with(coverAlbum)
            .load(intent.getStringExtra("artworkUrl100")?.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(dpToPx(8f, coverAlbum.context)))
            .into(coverAlbum)

        val collectionName: TextView = findViewById(R.id.albumValue)
        val collectionGroup: Group = findViewById(R.id.albumGroup)
        if (intent.getStringExtra("collectionName").isNullOrEmpty()) {
            collectionGroup.visibility = View.GONE
        } else {
            collectionName.text = intent.getStringExtra("collectionName")
        }

        val releaseDate: TextView = findViewById(R.id.yearValue)
        releaseDate.text = intent.getStringExtra("releaseDate")?.substringBefore('-')

        val genreName: TextView = findViewById(R.id.genreValue)
        genreName.text = intent.getStringExtra("genreName")

        val country: TextView = findViewById(R.id.countryValue)
        country.text = intent.getStringExtra("country")

        val timePlaying: TextView = findViewById(R.id.timePlaying)
        timePlaying.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(0)


    }
}