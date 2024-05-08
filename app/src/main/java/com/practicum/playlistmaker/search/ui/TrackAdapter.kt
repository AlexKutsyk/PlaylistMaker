package com.practicum.playlistmaker.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.search.domain.models.Track

class TrackAdapter(
    private val onTrackClickListener: OnItemClickListener,
) : RecyclerView.Adapter<TrackViewHolder>() {

    var trackList: MutableList<Track> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener { onTrackClickListener.onItemClick(trackList[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    fun interface OnItemClickListener {
        fun onItemClick(track: Track)
    }

}