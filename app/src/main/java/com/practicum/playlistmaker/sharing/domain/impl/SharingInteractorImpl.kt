package com.practicum.playlistmaker.sharing.domain.impl

import android.content.Context
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.sharing.domain.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.SharingInteractor
import com.practicum.playlistmaker.sharing.domain.models.EmailData

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator, val context: Context) : SharingInteractor {

    override fun shareApp() {
        externalNavigator.shareApp(getShareAppLink())
    }

    override fun openTerm() {
        externalNavigator.openTerm(getTermLink())
    }

    override fun openSupport() {
        externalNavigator.openSupport(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
        return context.getString(R.string.link_share)
    }

    private fun getSupportEmailData(): EmailData {
        val email = context.getString(R.string.email_adress)
        val subject = context.getString(R.string.email_subject)
        val message = context.getString(R.string.email_message)
        return EmailData(email, subject, message)
    }

    private fun getTermLink(): String {
        return context.getString(R.string.link_agreement)
    }
}