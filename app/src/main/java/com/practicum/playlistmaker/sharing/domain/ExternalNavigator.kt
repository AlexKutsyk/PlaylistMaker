package com.practicum.playlistmaker.sharing.domain

import com.practicum.playlistmaker.sharing.domain.models.EmailData

interface ExternalNavigator {
    fun shareApp(link: String)
    fun openTerm(link: String)
    fun openSupport(data: EmailData)
}