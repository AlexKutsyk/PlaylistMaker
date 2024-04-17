package com.practicum.playlistmaker.search.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmaker.databinding.FragmentSearchBinding
import com.practicum.playlistmaker.player.ui.KEY_TRACK
import com.practicum.playlistmaker.player.ui.PlayerActivity
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.presentation.SearchViewModel
import com.practicum.playlistmaker.search.presentation.models.HistoryListState
import com.practicum.playlistmaker.search.presentation.models.TrackState
import com.practicum.playlistmaker.util.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    private var inputText = ""
    private var simpleTextWatcher: TextWatcher? = null

    private lateinit var onTrackClickDebounce: (Track) -> Unit
    private var trackAdapter: TrackAdapter? = null
    private var adapterHistory: TrackAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trackAdapter = TrackAdapter { track ->
            onTrackClickDebounce(track)
            viewModel.makeHistoryList(track)
            hideKeyboard()
        }

        adapterHistory = TrackAdapter { track ->
            onTrackClickDebounce(track)
        }

        onTrackClickDebounce = debounce<Track>(
            CLICK_DEBOUNCE_DELAY_MILLIS,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { track ->
            startPlayer(track)
        }

        viewModel.getStateSearchLiveData().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.getStateListHistoryLiveData().observe(viewLifecycleOwner) {

            when (it) {

                is HistoryListState.Content -> {
                    binding.historySearch.isVisible = true
                    makeRecycleViewHistory(it.tracks)
                }

                is HistoryListState.Empty -> {
                    binding.historySearch.isVisible = false
                    makeRecycleViewHistory(it.tracks)
                }
            }
        }

        binding.clearText.setOnClickListener {
            binding.editTextSearch.setText("")
        }

        binding.buttonRefresh.setOnClickListener {
            binding.placeholderErrorConnect.visibility = View.GONE
            viewModel.search(inputText)
        }

        binding.buttonCleanHistory.setOnClickListener {
            viewModel.cleanListHistory()
        }

        simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearText.visibility = clearButtonVisibility(s)
                inputText = s?.toString() ?: ""
                viewModel.searchDebounce(inputText)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }

        binding.editTextSearch.addTextChangedListener(simpleTextWatcher)

        binding.editTextSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && binding.editTextSearch.text.isEmpty()) {
                viewModel.showListHistory()
            } else {
                binding.historySearch.visibility = View.GONE
            }
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.editTextSearch.hasFocus() && p0?.isEmpty() == true) {
                    viewModel.showListHistory()
                    binding.placeholderErrorSearch.isVisible = false
                    binding.placeholderErrorConnect.isVisible = false
                    binding.trackRecyclerView.isVisible = false
                } else {
                    trackAdapter?.trackList?.clear()
                    trackAdapter?.notifyDataSetChanged()
                    binding.trackRecyclerView.visibility = View.VISIBLE
                    binding.historySearch.visibility = View.GONE
                    binding.placeholderErrorSearch.isVisible = false
                    binding.placeholderErrorConnect.isVisible = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.trackRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.trackRecyclerView.adapter = trackAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.editTextSearch.removeTextChangedListener(simpleTextWatcher)
        binding.trackRecyclerView.adapter = null
        binding.trackHistoryRecyclerView.adapter = null
        _binding = null
        trackAdapter = null
        adapterHistory = null
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun makeRecycleViewHistory(trackListHistory: MutableList<Track>) {
        adapterHistory?.trackList = trackListHistory as ArrayList<Track>

        binding.trackHistoryRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.trackHistoryRecyclerView.adapter = adapterHistory
    }

    private fun startPlayer(track: Track) {
        val playerIntent = Intent(context, PlayerActivity::class.java)
        playerIntent.putExtra(KEY_TRACK, track)
        startActivity(playerIntent)
    }


    private fun showLoading() {
        binding.placeholderErrorConnect.visibility = View.GONE
        binding.placeholderErrorSearch.visibility = View.GONE
        binding.trackRecyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showContent(newTrackList: List<Track>) {
        binding.placeholderErrorConnect.visibility = View.GONE
        binding.placeholderErrorSearch.visibility = View.GONE
        binding.trackRecyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        trackAdapter?.trackList?.clear()
        trackAdapter?.trackList?.addAll(newTrackList)
        trackAdapter?.notifyDataSetChanged()
    }

    private fun showError() {
        binding.placeholderErrorConnect.visibility = View.GONE
        binding.placeholderErrorSearch.visibility = View.VISIBLE
        binding.trackRecyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun showErrorConnect() {
        binding.placeholderErrorConnect.visibility = View.VISIBLE
        binding.placeholderErrorSearch.visibility = View.GONE
        binding.trackRecyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun render(state: TrackState) {
        when (state) {
            is TrackState.Loading -> showLoading()
            is TrackState.Content -> showContent(state.tracks)
            is TrackState.Error -> showError()
            is TrackState.ErrorConnect -> showErrorConnect()
        }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private companion object {
        const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}