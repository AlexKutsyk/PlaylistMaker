package com.practicum.playlistmaker.sharing.domain.impl

import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.sharing.domain.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.SharingInteractor
import com.practicum.playlistmaker.sharing.domain.models.EmailDataId

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
) : SharingInteractor {

    override fun shareApp(link: String) {
        externalNavigator.shareApp(link)
    }

    override fun openTerm() {
        externalNavigator.openTerm(getTermIdLink())
    }

    override fun openSupport() {
        externalNavigator.openSupport(getSupportEmailIdData())
    }

    private fun getSupportEmailIdData(): EmailDataId {
        val emailId = R.string.email_adress
        val subjectId = R.string.email_subject
        val messageId = R.string.email_message
        return EmailDataId(emailId, subjectId, messageId)
    }

    private fun getTermIdLink(): Int {
        return R.string.link_agreement
    }
}