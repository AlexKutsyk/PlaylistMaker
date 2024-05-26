package com.practicum.playlistmaker.player.ui

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist

class PlayerWCoverViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
    private val imageCoverPlaylist: ImageView = view.findViewById(R.id.cover_playlist_linear)
    private val namePlaylist: TextView = view.findViewById(R.id.name_playlist_linear)
    private val amountTracks: TextView = view.findViewById(R.id.amount_tracks_linear)
    private val titleTracks: TextView = view.findViewById(R.id.title_track_linear)

    fun bind(model: Playlist) {
        imageCoverPlaylist.setImageURI(model.uriImageStorage)
        namePlaylist.text = model.namePlaylist
        amountTracks.text = model.amountTracks.toString()
        titleTracks.text = chooseEndingOfTrack(model.amountTracks)
    }

    private fun chooseEndingOfTrack(amount: Int): String {
        return if (amount % 100 in 11..19) {
            context.getString(R.string.trackov)
        } else {
            when (amount % 10) {
                1 -> context.getString(R.string.track)
                2, 3, 4 -> context.getString(R.string.tracka)
                else -> context.getString(R.string.trackov)
            }
        }
    }
}