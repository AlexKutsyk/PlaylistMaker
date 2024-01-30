package com.practicum.playlistmaker.domain.impl

import com.practicum.playlistmaker.domain.api.ApiMediaPlayerInteractor
import com.practicum.playlistmaker.domain.api.ApiMediaPlayerRepository

class MediaPlayerInteractorImpl(private val mediaPlayerRepository: ApiMediaPlayerRepository) :
    ApiMediaPlayerInteractor {

    override fun setDataSource(trackUrl: String?) {
        mediaPlayerRepository.setDataSource(trackUrl)
    }

    override fun prepareAsync() {
        mediaPlayerRepository.prepareAsync()
    }

    override fun setOnPreparedListener(param: () -> Unit) {
        mediaPlayerRepository.setOnPreparedListener(param)
    }

    override fun setOnCompletionListener(param: () -> Unit) {
        mediaPlayerRepository.setOnCompletionListener(param)
    }

    override fun start() {
        mediaPlayerRepository.start()
    }

    override fun pause() {
        mediaPlayerRepository.pause()
    }

    override fun release() {
        mediaPlayerRepository.release()
    }

    override fun currentPosition(): Int {
        return mediaPlayerRepository.currentPosition()
    }

}