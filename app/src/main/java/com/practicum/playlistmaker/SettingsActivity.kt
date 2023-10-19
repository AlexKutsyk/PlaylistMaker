package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }

        val buttonShare = findViewById<ImageButton>(R.id.button_share)
        buttonShare.setOnClickListener {
            val message = getString(R.string.link_share)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            if (shareIntent.resolveActivity(packageManager) != null) {
                startActivity(shareIntent)
            }
        }

        val buttonSupport = findViewById<ImageButton>(R.id.button_support)
        buttonSupport.setOnClickListener {
            val subject = getString(R.string.subject_message)
            val message = getString(R.string.message)
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.adress)))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }

        val buttonUserAgreement = findViewById<ImageButton>(R.id.button_user_agreement)
        buttonUserAgreement.setOnClickListener {
            val site = Uri.parse(getString(R.string.link_agreement))
            val shareIntent = Intent(Intent.ACTION_VIEW, site)
            startActivity(shareIntent)
        }
    }
}
