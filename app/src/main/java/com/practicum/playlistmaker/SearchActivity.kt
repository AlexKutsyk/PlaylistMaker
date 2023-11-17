package com.practicum.playlistmaker

import android.content.Context
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private var inputText = TEXT_DEF

    private val iTunesBaseURL = "https://itunes.apple.com"

    private val retrofit =
        Retrofit.Builder().baseUrl(iTunesBaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val iTunesService = retrofit.create(ITunesAPI::class.java)

    private var tracks = ArrayList<Track>()

    private val adapter = TrackAdapter(tracks)

    private lateinit var backButton: ImageView
    private lateinit var clearButton: ImageView
    private lateinit var inputEditText: EditText
    private lateinit var placeholderErrorSearch: LinearLayout
    private lateinit var placeholderErrorConnect: LinearLayout
    private lateinit var trackList: RecyclerView
    private lateinit var refreshButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        backButton = findViewById(R.id.button_back)
        clearButton = findViewById(R.id.clear_text)
        inputEditText = findViewById(R.id.edit_text_search)
        trackList = findViewById(R.id.track_recycleView)
        placeholderErrorSearch = findViewById(R.id.placeholder_error_search)
        placeholderErrorConnect = findViewById(R.id.placeholder_error_connect)
        refreshButton = findViewById(R.id.button_refresh)

        backButton.setOnClickListener {
            finish()
        }

        clearButton.setOnClickListener {
            inputEditText.setText("")
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(clearButton.windowToken, 0)
            tracks.clear()
            adapter.notifyDataSetChanged()
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

        adapter.trackList = tracks
        trackList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
                    call: Call<TracksResponse>,
                    response: Response<TracksResponse>
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


    private companion object {
        const val TEMP_TEXT = "TEMP_TEXT"
        const val TEXT_DEF = ""
    }
}