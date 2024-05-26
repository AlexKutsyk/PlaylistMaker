package com.practicum.playlistmaker.library.playlist.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.FragmentPlaylistCreatorBinding
import com.practicum.playlistmaker.library.playlist.presentation.PlaylistCreatorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream


open class PlaylistCreatorFragment : Fragment() {

    private var _binding: FragmentPlaylistCreatorBinding? = null
    open val binding get() = _binding!!

    open val viewModel: PlaylistCreatorViewModel by viewModel()

    private val placeholder = R.drawable.placeholder

    private var titleTextWatcher: TextWatcher? = null
    private var descriptionTextWatcher: TextWatcher? = null

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    private var uri: Uri? = null

    var uriImageStorage: Uri? = null

    private val currentTime by lazy { System.currentTimeMillis() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistCreatorBinding.inflate(inflater, container, false)

        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

            this.uri = uri

            if (uri == null) {
                binding.coverPlaylist.background = null
                binding.coverPlaylist.setImageResource(placeholder)
            } else {
                binding.coverPlaylist.apply {
                    setImageURI(uri)
                    background =
                        ContextCompat.getDrawable(context, R.drawable.cover_playlist_background)
                    clipToOutline = true
                    outlineProvider = ViewOutlineProvider.BACKGROUND
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            exitFromPlaylistCreator()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitFromPlaylistCreator()
                }
            })

        binding.coverPlaylist.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.createButton.setOnClickListener {

            saveImageCover()

            Toast.makeText(
                requireContext(),
                getString(R.string.playlist_created, binding.nameEditText.text), Toast.LENGTH_SHORT
            ).show()

            viewModel.createPlaylist(
                binding.nameEditText.text.toString(),
                binding.descriptionEditText.text.toString(),
                uriImageStorage
            )

            requireActivity().supportFragmentManager.popBackStack()
        }

        titleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.apply {
                        createButton.isActivated = false
                        nameEditText.isActivated = false
                        hintNameTextView.isVisible = false
                    }

                } else {
                    binding.apply {
                        createButton.isActivated = true
                        nameEditText.isActivated = true
                        hintNameTextView.isVisible = true
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        descriptionTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.apply {
                        descriptionEditText.isActivated = false
                        hintDescriptionTextView.isVisible = false
                    }

                } else {
                    binding.apply {
                        descriptionEditText.isActivated = true
                        hintDescriptionTextView.isVisible = true
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        binding.nameEditText.addTextChangedListener(titleTextWatcher)
        binding.descriptionEditText.addTextChangedListener(descriptionTextWatcher)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showExitDialog() {
        MaterialAlertDialogBuilder(requireContext(), R.style.PlaylistMakerDialogStyle).setTitle(getString(R.string.complete_making_playlist))
            .setMessage(getString(R.string.data_will_lose))
            .setPositiveButton(getString(R.string.complete)) { _, _ ->
                requireActivity().supportFragmentManager.popBackStack()
            }.setNegativeButton(getString(R.string.cancel)) { _, _ ->
                //
            }.show()
    }

    private fun exitFromPlaylistCreator() {
        if (uri == null && binding.nameEditText.text.isNullOrEmpty() && binding.descriptionEditText.text.isNullOrEmpty()) {
            requireActivity().supportFragmentManager.popBackStack()
        } else {
            showExitDialog()
        }
    }

    fun saveImageCover() {

        if (uri != null) {

            val imageCoverPath = File(
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image_cover"
            )

            if (!imageCoverPath.exists()) {
                imageCoverPath.mkdir()
            }
            val imageCoverFile = File(imageCoverPath, "$currentTime.jpg")
            val inputStream = requireContext().contentResolver.openInputStream(uri!!)
            val outputStream = FileOutputStream(imageCoverFile)
            uriImageStorage = Uri.fromFile(imageCoverFile)

            BitmapFactory.decodeStream(inputStream)
                .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

        }
    }

}

