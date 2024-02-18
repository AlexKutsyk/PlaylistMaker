package com.practicum.playlistmaker.sharing.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.practicum.playlistmaker.sharing.domain.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.models.EmailData

class ExternalNavigatorImpl(val context: Context) : ExternalNavigator {

    override fun shareApp(link: String) {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, link)
            if (resolveActivity(context.packageManager) != null) {
                this.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }
    }

    override fun openTerm(link: String) {
        val site = Uri.parse(link)
        Intent(Intent.ACTION_VIEW, site).apply {
            this.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    override fun openSupport(value: EmailData) {
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(value.adress))
            putExtra(Intent.EXTRA_SUBJECT, value.subject)
            putExtra(Intent.EXTRA_TEXT, value.message)
            this.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }
}