package com.practicum.playlistmaker.player.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist

class PlayerAdapter(
    private val onPlaylistClickListener: OnPlaylistClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var playlists: MutableList<Playlist> = mutableListOf()

    override fun getItemViewType(position: Int): Int {

        val itemPlaylist = playlists[position]

        return when (itemPlaylist.uriImageStorage.toString()) {
            "null" -> 1
            else -> 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.playlist_linear_item, parent, false)

        return when (viewType) {
            1 -> PlayerWOCoverViewHolder(view, parent.context)
            else -> PlayerWCoverViewHolder(view, parent.context)
        }
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener { onPlaylistClickListener.onPlaylistClick(playlists[position]) }

        when (holder) {
            is PlayerWOCoverViewHolder -> {
                holder.bind(playlists[position])
            }
            is PlayerWCoverViewHolder -> {
                holder.bind((playlists[position]))
            }
        }

    }

    fun interface OnPlaylistClickListener {
        fun onPlaylistClick(playlist: Playlist)
    }
}