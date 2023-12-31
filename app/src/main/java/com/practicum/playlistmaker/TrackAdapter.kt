package com.practicum.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter (
    var trackList: ArrayList<Track>,
    val onTrackClickListener: OnItemClickListener
) : RecyclerView.Adapter <TrackViewHolder> () {

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
}