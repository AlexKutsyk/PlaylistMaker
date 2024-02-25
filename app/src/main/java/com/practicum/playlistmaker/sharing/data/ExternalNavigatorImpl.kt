package com.practicum.playlistmaker.sharing.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.practicum.playlistmaker.sharing.domain.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.models.EmailData
import com.practicum.playlistmaker.sharing.domain.models.EmailDataId

class ExternalNavigatorImpl(val context: Context) : ExternalNavigator {

    override fun shareApp(linkId: Int) {
        val link = getShareAppLink(linkId)
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, link)
            if (resolveActivity(context.packageManager) != null) {
                this.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }
    }

    override fun openTerm(linkId: Int) {
        val link = getTermLink(linkId)
        val site = Uri.parse(link)
        Intent(Intent.ACTION_VIEW, site).apply {
            this.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    override fun openSupport(dataId: EmailDataId) {
        val mailData = getSupportEmailData(dataId)
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(mailData.adress))
            putExtra(Intent.EXTRA_SUBJECT, mailData.subject)
            putExtra(Intent.EXTRA_TEXT, mailData.message)
            this.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    private fun getShareAppLink(linkId: Int): String {
        return context.getString(linkId)
    }

    private fun getTermLink(linkId: Int): String {
        return context.getString(linkId)
    }

    private fun getSupportEmailData(dataId: EmailDataId): EmailData {
        val email = context.getString(dataId.idAdress)
        val subject = context.getString(dataId.idSubject)
        val message = context.getString(dataId.idMessage)
        return EmailData(email, subject, message)
    }
}