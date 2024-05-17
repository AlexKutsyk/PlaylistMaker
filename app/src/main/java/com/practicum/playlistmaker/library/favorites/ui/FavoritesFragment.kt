package com.practicum.playlistmaker.library.favorites.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmaker.databinding.FragmentFavoritesBinding
import com.practicum.playlistmaker.library.favorites.presentation.FavoritesViewModel
import com.practicum.playlistmaker.player.ui.KEY_TRACK
import com.practicum.playlistmaker.player.ui.PlayerActivity
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.ui.TrackAdapter
import com.practicum.playlistmaker.util.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavoritesViewModel>()

    private var adapterFavorite: TrackAdapter? = null
    private lateinit var onTrackClickDebounce: (Track) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterFavorite = TrackAdapter { track ->
            onTrackClickDebounce(track)
        }

        onTrackClickDebounce = debounce<Track>(
            CLICK_DEBOUNCE_DELAY_MILLIS,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { track ->
            startPlayer(track)
        }

        binding.favoritesTracksRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.favoritesTracksRecyclerView.adapter = adapterFavorite

        viewModel.getStateFavoritesTracks().observe(viewLifecycleOwner) { favoriteState ->
            adapterFavorite?.trackList?.clear()
            favoriteState.listFavoritesTracks?.let { list -> adapterFavorite?.trackList?.addAll(list) }
            adapterFavorite?.notifyDataSetChanged()

            binding.apply {
                favoritesTracksRecyclerView.isVisible = favoriteState.isVisibleFavorites
                placeholderNoFavorites.isVisible = favoriteState.isVisiblePlaseholder
            }

        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFavoritesTracks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.favoritesTracksRecyclerView.adapter = null
        _binding = null
        adapterFavorite = null
    }


    private fun startPlayer(track: Track) {
        val playerIntent = Intent(
            context,
            PlayerActivity::class.java
        )
        playerIntent.putExtra(
            KEY_TRACK,
            track
        )
        startActivity(playerIntent)
    }

    companion object {
        fun newInstance() = FavoritesFragment()
        const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }

}