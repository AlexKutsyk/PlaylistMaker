package com.practicum.playlistmaker.sharing.domain

import com.practicum.playlistmaker.sharing.domain.models.EmailDataId

interface ExternalNavigator {
    fun shareApp(linkId: Int)
    fun openTerm(linkId: Int)
    fun openSupport(dataId: EmailDataId)
}