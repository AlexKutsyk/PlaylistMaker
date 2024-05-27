package com.practicum.playlistmaker.library.playlist.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.FragmentPlaylistBinding
import com.practicum.playlistmaker.library.playlist.domain.models.Playlist
import com.practicum.playlistmaker.library.playlist.presentation.PlaylistViewModel
import com.practicum.playlistmaker.library.playlist.presentation.models.TracksOfPlaylistState
import com.practicum.playlistmaker.player.ui.KEY_TRACK
import com.practicum.playlistmaker.player.ui.PlayerActivity
import com.practicum.playlistmaker.search.domain.models.Track
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.time.Duration.Companion.milliseconds

class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<PlaylistViewModel>()

    private val playlistId: Int by lazy { requireArguments().getInt(ARG_PLAYLIST) }

    private val onClickTrack = object : TracksOfPlaylistAdapter.OnClickTrackListener {
        override fun onClickTrack(track: Track) {
            startPlayer(track)
        }

        override fun onLongClickTrack(track: Track) {
            MaterialAlertDialogBuilder(requireContext(), R.style.PlaylistMakerDialogStyle)
                .setTitle(getString(R.string.delete_track))
                .setMessage(getString(R.string.are_you_sure_you_want_to_delete_the_track_from_playlist))
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                    //
                }.setPositiveButton(getString(R.string.delete)) { _, _ ->
                    viewModel.deleteSelectedTrackFromPlaylist(track)
                }.show()
        }
    }

    private var adapterTracksOfPlaylist = TracksOfPlaylistAdapter(onClickTrack)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPlaylist(playlistId)
        viewModel.getTracksByPlaylistId(playlistId)

        binding.trackRecyclerView.adapter = adapterTracksOfPlaylist

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.menuBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.isVisible = false
                    }

                    else -> {
                        binding.overlay.isVisible = true
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //
            }
        })

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.menuButton.setOnClickListener {
            binding.apply {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.textDeletePlaylist.setOnClickListener {

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            MaterialAlertDialogBuilder(
                requireContext(),
                R.style.PlaylistMakerDialogStyle
            ).setMessage(getString(R.string.do_you_wont_to_delete_playlist))
                .setNegativeButton(getString(R.string.no)) { _, _ ->
                    //
                }.setPositiveButton(getString(R.string.yes)) { _, _ ->
                    viewModel.deletePlaylistById(playlistId)
                }.show()
        }

        binding.textEditInformation.setOnClickListener {
            findNavController().navigate(
                R.id.action_playlistFragment_to_playlistEditorFragment,
                PlaylistEditorFragment.createArgs(playlistId)
            )
        }

        viewModel.getPlaylistState().observe(viewLifecycleOwner) { playlist ->
            renderTracksBottomSheet(playlist)
            renderMenuBottomSheet(playlist)
        }

        viewModel.getListTracksOfPlaylistState()
            .observe(viewLifecycleOwner) { tracksOfPlaylistState ->
                when (tracksOfPlaylistState) {

                    is TracksOfPlaylistState.ContentPlaylist -> {

                        val reverseListTracksOfPlaylist =
                            tracksOfPlaylistState.listTracksOfPlaylist.reversed()
                        adapterTracksOfPlaylist.listTracksOfPlaylist =
                            reverseListTracksOfPlaylist as MutableList<Track>
                        adapterTracksOfPlaylist.notifyDataSetChanged()

                        binding.buttonShare.setOnClickListener {
                            viewModel.shareLinkPlaylist(playlistId)
                        }

                        binding.textShare.setOnClickListener {
                            viewModel.shareLinkPlaylist(playlistId)
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                        }
                    }

                    is TracksOfPlaylistState.EmptyPlaylist -> {

                        binding.apply {
                            trackRecyclerView.isVisible = false
                            textMessage.isVisible = true
                        }


                        binding.buttonShare.setOnClickListener {
                            messageEmptyPlaylist()
                        }

                        binding.textShare.setOnClickListener {
                            messageEmptyPlaylist()
                        }
                    }
                }
            }

        viewModel.getStateDelete().observe(viewLifecycleOwner) { deleteState ->
            if (deleteState.isComplete) {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startPlayer(track: Track) {
        val playerIntent = Intent(
            context, PlayerActivity::class.java
        )
        playerIntent.putExtra(
            KEY_TRACK, track
        )
        startActivity(playerIntent)
    }

    private fun messageEmptyPlaylist() {
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.playlist_does_not_have_tracks),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun renderTracksBottomSheet(playlist: Playlist) {
        binding.apply {
            namePlaylist.text = playlist.namePlaylist
            if (playlist.descriptionPlaylist.isNullOrEmpty()) {
                descriptionPlaylist.isVisible = false
            } else {
                descriptionPlaylist.isVisible = true
                descriptionPlaylist.text = playlist.descriptionPlaylist
            }
            coverPlaylist.setImageURI(playlist.uriImageStorage)
            totalTime.text =
                "${playlist.totalPlaylistTime.milliseconds.inWholeMinutes} ${playlist.minutesSpelling}"
            amountTracks.text = "${playlist.amountTracks} ${playlist.trackSpelling}"
        }
    }

    private fun renderMenuBottomSheet(playlist: Playlist) {
        binding.apply {
            if (playlist.uriImageStorage.toString() != getString(R.string.null_title)) {
                coverPlaylistLinear.setImageURI(playlist.uriImageStorage)
            }
            namePlaylistLinear.text = playlist.namePlaylist
            amountTracksLinear.text = playlist.amountTracks.toString()
            titleTrackLinear.text = playlist.trackSpelling
        }
    }

    companion object {
        const val ARG_PLAYLIST = "playlist"
        fun createArgs(playlistID: Int): Bundle = bundleOf(ARG_PLAYLIST to playlistID)
    }
}