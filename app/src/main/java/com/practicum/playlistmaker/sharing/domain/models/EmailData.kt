package com.practicum.playlistmaker.sharing.domain.models

data class EmailData(
    val address: String,
    val subject: String,
    val message: String,
)
