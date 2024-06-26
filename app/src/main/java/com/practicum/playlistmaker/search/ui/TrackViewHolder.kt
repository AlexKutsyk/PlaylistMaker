package com.practicum.playlistmaker.search.ui

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.search.domain.models.Track
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

class TrackViewHolder(
    itemView: View,
    val context: Context,
) : RecyclerView.ViewHolder(itemView) {

    private val trackName = itemView.findViewById<TextView>(R.id.name_track)
    private val artistName = itemView.findViewById<TextView>(R.id.name_artist)
    private val trackTime = itemView.findViewById<TextView>(R.id.time_track)
    private val imageTrack = itemView.findViewById<ImageView>(R.id.image_track)

    fun bind(model: Track) {
        trackName.text = model.trackName
        artistName.requestLayout()
        artistName.text = model.artistName
        trackTime.text =
            "${model.trackTimeMillis?.milliseconds?.inWholeMinutes}:" + SimpleDateFormat(
                context.getString(R.string.ss_second), Locale.getDefault()
            ).format(model.trackTimeMillis)
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(2f, imageTrack.context)))
            .into(imageTrack)
    }
}

fun dpToPx(
    dp: Float,
    context: Context
): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    ).toInt()
}


