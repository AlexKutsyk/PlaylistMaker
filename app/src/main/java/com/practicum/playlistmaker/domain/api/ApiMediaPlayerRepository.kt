package com.practicum.playlistmaker.domain.api

interface ApiMediaPlayerRepository {

    fun setDataSource(trackUrl: String?)

    fun prepareAsync()

    fun setOnPreparedListener(param: () -> Unit)

    fun setOnCompletionListener(param: () -> Unit)

    fun start()

    fun pause()

    fun release()

    fun currentPosition(): Int

}