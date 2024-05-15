package com.practicum.playlistmaker.player.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist

class PlayerAdapter(
private val onPlaylistClickListener: OnPlaylistClickListener
): RecyclerView.Adapter<PlayerViewHolder>() {

    var playlists: MutableList<Playlist> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_linear_item, parent, false)
        return PlayerViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(playlists[position])
        holder.itemView.setOnClickListener { onPlaylistClickListener.onPlaylistClick(playlists[position]) }
    }

    fun interface OnPlaylistClickListener {
        fun onPlaylistClick(playlist: Playlist)
    }
}