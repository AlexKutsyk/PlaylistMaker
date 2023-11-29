package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.google.android.material.switchmaterial.SwitchMaterial

const val SETTINGS = "shared_preferences_settings"
const val SWITCH_KEY = "key_for_switch"

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<ImageButton>(R.id.button_back)
        buttonBack.setOnClickListener {
            finish()
        }

        val buttonShare = findViewById<ImageButton>(R.id.button_share)
        buttonShare.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                val message = getString(R.string.link_share)
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
                if (resolveActivity(packageManager) != null) {
                    startActivity(this)
                }
            }
        }

        val buttonSupport = findViewById<ImageButton>(R.id.button_support)
        buttonSupport.setOnClickListener {
            Intent(Intent.ACTION_SENDTO).apply {
                val subject = getString(R.string.subject_message)
                val message = getString(R.string.message)
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.adress)))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
                startActivity(this)
            }
        }

        val buttonUserAgreement = findViewById<ImageButton>(R.id.button_user_agreement)
        buttonUserAgreement.setOnClickListener {
            val site = Uri.parse(getString(R.string.link_agreement))
            val shareIntent = Intent(Intent.ACTION_VIEW, site)
            startActivity(shareIntent)
        }

        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)
        val sharedPrefs = getSharedPreferences(SETTINGS, MODE_PRIVATE)
        themeSwitcher.isChecked = sharedPrefs.getBoolean(SWITCH_KEY, false)
        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
        }

    }

    override fun onPause() {
        super.onPause()
        val sharedPrefs = getSharedPreferences(SETTINGS, MODE_PRIVATE)
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)
        sharedPrefs.edit()
            .putBoolean(SWITCH_KEY, themeSwitcher.isChecked)
            .apply()
    }
}
