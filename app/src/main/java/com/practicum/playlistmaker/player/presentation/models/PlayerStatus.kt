package com.practicum.playlistmaker.player.presentation.models

sealed class PlayerStatus(
    val progress: String,
    val isPlayButtonVisible: Boolean,
    val isPauseButtonVisible: Boolean,
    ) {
    class Prepared: PlayerStatus("00:00", true, false)
    class Playing(progress: String): PlayerStatus(progress, false, true)
    class Pause(progress: String): PlayerStatus(progress, true, false)
    class Finished: PlayerStatus("00:00", true, false)
}
