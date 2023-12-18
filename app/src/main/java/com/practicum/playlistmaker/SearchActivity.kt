package com.practicum.playlistmaker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private var inputText = TEXT_DEF

    private val iTunesBaseURL = "https://itunes.apple.com"

    private val retrofit =
        Retrofit.Builder().baseUrl(iTunesBaseURL).addConverterFactory(GsonConverterFactory.create())
            .build()

    private val iTunesService = retrofit.create(ITunesAPI::class.java)

    private var tracks = ArrayList<Track>()
    private var trackListHistory: MutableList<Track> = mutableListOf()

    private lateinit var adapter: TrackAdapter
    private lateinit var adapterHistory: TrackAdapter


    private lateinit var backButton: ImageView
    private lateinit var clearButton: ImageView
    private lateinit var inputEditText: EditText
    private lateinit var placeholderErrorSearch: LinearLayout
    private lateinit var placeholderErrorConnect: LinearLayout
    private lateinit var trackList: RecyclerView
    private lateinit var refreshButton: Button
    private lateinit var historySearch: LinearLayout
    private lateinit var recyclerViewHistory: RecyclerView
    private lateinit var cleanHistory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        backButton = findViewById(R.id.button_back)
        clearButton = findViewById(R.id.clear_text)
        inputEditText = findViewById(R.id.edit_text_search)
        trackList = findViewById(R.id.track_recyclerView)
        placeholderErrorSearch = findViewById(R.id.placeholder_error_search)
        placeholderErrorConnect = findViewById(R.id.placeholder_error_connect)
        refreshButton = findViewById(R.id.button_refresh)
        historySearch = findViewById(R.id.history_search)
        recyclerViewHistory = findViewById(R.id.track_history_recyclerView)
        cleanHistory = findViewById(R.id.button_clean_history)


        backButton.setOnClickListener {
            finish()
        }

        clearButton.setOnClickListener {
            inputEditText.setText("")
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(clearButton.windowToken, 0)
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputText = s.toString()
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)
        inputEditText.setText(inputText)

        val sharedPrefs = getSharedPreferences(STORAGE, MODE_PRIVATE)
        val searchHistory = SearchHistory(sharedPrefs)


        val onTrackClickListener = object : OnItemClickListener {
            override fun onItemClick(track: Track) {
                trackListHistory.removeAll { it.trackId == track.trackId }
                trackListHistory.add(0, track)
                trackListHistory = trackListHistory.take(10).toMutableList()
                searchHistory.saveSearchHistory(trackListHistory)
                startPlayer(track)
            }
        }

        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && inputEditText.text.isEmpty()) {
                trackListHistory = searchHistory.readSearchHistory()!!.toMutableList()
                makeRecycleViewHistory(onTrackClickListener)
                if (trackListHistory.isNotEmpty()) {
                    historySearch.visibility = View.VISIBLE
                } else {
                    historySearch.visibility = View.GONE
                }
                cleanHistory.setOnClickListener {
                    historySearch.visibility = View.GONE
                    searchHistory.cleanSearchHistory()
                    trackListHistory = searchHistory.readSearchHistory()!!.toMutableList()
                    makeRecycleViewHistory(onTrackClickListener)
                }
            } else {
                historySearch.visibility = View.GONE
            }
        }

        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (inputEditText.hasFocus() && p0?.isEmpty() == true) {
                    trackList.visibility = View.GONE
                    tracks.clear()
                    trackListHistory = searchHistory.readSearchHistory()!!.toMutableList()
                    makeRecycleViewHistory(onTrackClickListener)
                    if (trackListHistory.isNotEmpty()) {
                        historySearch.visibility = View.VISIBLE
                    } else {
                        historySearch.visibility = View.GONE
                    }

                    cleanHistory.setOnClickListener {
                        historySearch.visibility = View.GONE
                        searchHistory.cleanSearchHistory()
                        trackListHistory = searchHistory.readSearchHistory()!!.toMutableList()
                        makeRecycleViewHistory(onTrackClickListener)
                    }
                } else {
                    trackList.visibility = View.VISIBLE
                    historySearch.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        adapter = TrackAdapter(tracks, onTrackClickListener)
        adapter.trackList = tracks

        trackList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        trackList.adapter = adapter


        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
            }
            false
        }

    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEMP_TEXT, inputText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        inputText = savedInstanceState.getString(TEMP_TEXT, TEXT_DEF)
    }

    private fun search() {
        iTunesService.findTrack(inputEditText.text.toString())
            .enqueue(object : Callback<TracksResponse> {
                override fun onResponse(
                    call: Call<TracksResponse>, response: Response<TracksResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            if (response.body()?.results?.isNotEmpty() == true) {
                                tracks.clear()
                                placeholderErrorSearch.visibility = View.GONE
                                tracks.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                            } else {
                                tracks.clear()
                                adapter.notifyDataSetChanged()
                                placeholderErrorSearch.visibility = View.VISIBLE
                            }
                        }

                        else -> {
                            tracks.clear()
                            adapter.notifyDataSetChanged()
                            placeholderErrorConnect.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                    t.printStackTrace()
                    tracks.clear()
                    adapter.notifyDataSetChanged()
                    placeholderErrorConnect.visibility = View.VISIBLE
                    refreshButton.setOnClickListener {
                        placeholderErrorConnect.visibility = View.GONE
                        search()
                    }
                }
            })
    }

    fun makeRecycleViewHistory(onTrackClickListener: OnItemClickListener) {
        adapterHistory =
            TrackAdapter(trackListHistory as ArrayList<Track>, onTrackClickListener)
        adapterHistory.trackList = trackListHistory as ArrayList<Track>

        recyclerViewHistory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewHistory.adapter = adapterHistory
    }

    fun startPlayer(track: Track) {
        val playerIntent = Intent(this@SearchActivity, PlayerActivity::class.java)
        playerIntent.putExtra("trackName", track.trackName)
        playerIntent.putExtra("artistName", track.artistName)
        playerIntent.putExtra("trackTimeMillis", track.trackTimeMillis)
        playerIntent.putExtra("artworkUrl100", track.artworkUrl100)
        playerIntent.putExtra("collectionName", track.collectionName)
        playerIntent.putExtra("releaseDate", track.releaseDate)
        playerIntent.putExtra("genreName", track.primaryGenreName)
        playerIntent.putExtra("country", track.country)
        startActivity(playerIntent)
    }

    private companion object {
        const val TEMP_TEXT = "TEMP_TEXT"
        const val TEXT_DEF = ""
    }
}