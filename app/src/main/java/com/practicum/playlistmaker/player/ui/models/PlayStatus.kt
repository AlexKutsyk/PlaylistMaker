package com.practicum.playlistmaker.player.ui.models

sealed interface PlayStatus {
    object Loading: PlayStatus
    data class Play(val progress: Long): PlayStatus
    object Pause: PlayStatus
    object Finish: PlayStatus
}
