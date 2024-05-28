package com.practicum.playlistmaker.library.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.practicum.playlistmaker.library.favorites.ui.FavoritesFragment
import com.practicum.playlistmaker.library.playlist.ui.ListPlaylistsFragment

class LibraryAdapter(
    fragmentManager: FragmentManager,
    lifecycle: androidx.lifecycle.Lifecycle
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) FavoritesFragment.newInstance() else ListPlaylistsFragment.newInstance()
    }
}