package com.practicum.playlistmaker.library.playlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist

class PlaylistAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var playlists: MutableList<Playlist> = mutableListOf()

    override fun getItemViewType(position: Int): Int {

        val itemPlaylist = playlists[position]

        return when (itemPlaylist.uriImageStorage.toString()) {
            "null" -> 1
            else -> 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_grid_item, parent, false)
        return when (viewType) {
            1 -> PlaylistWOCoverViewHolder(view, parent.context)
            else -> PlaylistWCoverViewHolder(view, parent.context)
        }
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlaylistWOCoverViewHolder -> holder.bind(playlists[position])
            is PlaylistWCoverViewHolder -> holder.bind(playlists[position])
        }

    }
}