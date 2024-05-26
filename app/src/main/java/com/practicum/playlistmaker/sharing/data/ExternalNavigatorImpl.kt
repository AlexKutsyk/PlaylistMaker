package com.practicum.playlistmaker.sharing.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.sharing.domain.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.models.EmailData
import com.practicum.playlistmaker.sharing.domain.models.EmailDataId

class ExternalNavigatorImpl(val context: Context) : ExternalNavigator {

    override fun shareApp(link: String) {
        Intent(Intent.ACTION_SEND).apply {
            type = context.getString(R.string.text_plain)
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
            data = Uri.parse(context.getString(R.string.mailto))
            putExtra(Intent.EXTRA_EMAIL, arrayOf(mailData.address))
            putExtra(Intent.EXTRA_SUBJECT, mailData.subject)
            putExtra(Intent.EXTRA_TEXT, mailData.message)
            this.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    private fun getTermLink(linkId: Int): String {
        return context.getString(linkId)
    }

    private fun getSupportEmailData(dataId: EmailDataId): EmailData {
        val email = context.getString(dataId.idAddress)
        val subject = context.getString(dataId.idSubject)
        val message = context.getString(dataId.idMessage)
        return EmailData(email, subject, message)
    }
}