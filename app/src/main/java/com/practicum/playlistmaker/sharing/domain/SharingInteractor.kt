package com.practicum.playlistmaker.sharing.domain

interface SharingInteractor {
    fun shareApp(link: String)
    fun openTerm()
    fun openSupport()
}