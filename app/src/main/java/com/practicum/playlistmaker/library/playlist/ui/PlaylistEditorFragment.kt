package com.practicum.playlistmaker.library.playlist.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import com.practicum.playlistmaker.library.playlist.presentation.PlaylistEditorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlaylistEditorFragment : PlaylistCreatorFragment() {

    override val viewModel: PlaylistEditorViewModel by viewModel {
        parametersOf(playlistId)
    }

    val playlistId: Int by lazy { requireArguments().getInt(ARG_PLAYLIST_ID) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderPlaylistEditScreen()

        binding.backButton.setOnClickListener {
            exitFromPlaylistEditFragment()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitFromPlaylistEditFragment()
                }
            })

        viewModel.getPlaylistState().observe(viewLifecycleOwner) { playlist ->

            setPlaylistData(playlist)
            var newUriCoverImageStorage = playlist.uriImageStorage
            var isImageCoverChanged = false

            binding.coverPlaylist.setOnClickListener {
                isImageCoverChanged = true
                launchPickMedia()
            }

            binding.createButton.setOnClickListener {

                if (binding.createButton.isActivated) {

                    if (isImageCoverChanged) newUriCoverImageStorage = saveImageCover()

                    viewModel.saveUpdatesPlaylist(
                        playlist,
                        binding.nameEditText.text.toString(),
                        binding.descriptionEditText.text.toString(),
                        newUriCoverImageStorage
                    )

                    exitFromPlaylistEditFragment()
                }
            }
        }

    }

    private fun renderPlaylistEditScreen() {
        binding.apply {
            newPlaylistTextView.text = getString(R.string.edit_title)
            createButton.text = getString(R.string.save)
        }
    }

    private fun setPlaylistData(playlist: Playlist) {
        binding.apply {
            nameEditText.setText(playlist.namePlaylist)
            descriptionEditText.setText(playlist.descriptionPlaylist)
            if (playlist.uriImageStorage.toString() != "null") setCoverPlaylist(playlist.uriImageStorage!!)
        }
    }

    private fun exitFromPlaylistEditFragment() {
        findNavController().navigateUp()
    }

    companion object {
        const val ARG_PLAYLIST_ID = "playlistId"
        fun createArgs(playlistId: Int): Bundle = bundleOf(ARG_PLAYLIST_ID to playlistId)
    }

}