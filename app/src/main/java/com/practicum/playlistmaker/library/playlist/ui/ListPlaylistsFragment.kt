package com.practicum.playlistmaker.library.playlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.FragmentListPlaylistsBinding
import com.practicum.playlistmaker.library.playlist.presentation.ListPlaylistsViewModel
import com.practicum.playlistmaker.library.playlist.presentation.models.PlaylistState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListPlaylistsFragment : Fragment() {

    private var _binding: FragmentListPlaylistsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ListPlaylistsViewModel>()

    private val adapterPlaylist = ListPlaylistsAdapter { playlist ->
        findNavController().navigate(R.id.action_libraryFragment_to_playlistFragment, PlaylistFragment.createArgs(playlist.id))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPlaylistsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playlistsRecycleView.adapter = adapterPlaylist

        binding.buttonNewPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_to_playlistCreatorFragment)
        }

        viewModel.getListPlaylistsState().observe(viewLifecycleOwner) { playlistState ->
            when (playlistState) {
                is PlaylistState.Content -> {
                    binding.placeholderNoPlaylists.isVisible = false
                    binding.playlistsRecycleView.isVisible = true
                    adapterPlaylist.playlists = playlistState.playlists
                    adapterPlaylist.notifyDataSetChanged()
                }

                is PlaylistState.Empty -> {
                    binding.placeholderNoPlaylists.isVisible = true
                    binding.playlistsRecycleView.isVisible = false
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getListPlaylistsFromDB()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ListPlaylistsFragment()
    }
}