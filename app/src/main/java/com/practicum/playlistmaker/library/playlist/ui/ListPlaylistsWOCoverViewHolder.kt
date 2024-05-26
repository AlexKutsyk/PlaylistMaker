package com.practicum.playlistmaker.library.playlist.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist

class ListPlaylistsWOCoverViewHolder(
    view: View,
): RecyclerView.ViewHolder(view) {

        private val namePlaylist: TextView = view.findViewById(R.id.name_playlist)
        private val amountTracks: TextView = view.findViewById(R.id.amount_tracks)
        private val titleTracks: TextView = view.findViewById(R.id.title_track)

        fun bind(model: Playlist) {
            namePlaylist.text = model.namePlaylist
            amountTracks.text = model.amountTracks.toString()
            titleTracks.text = model.trackSpelling
        }

}