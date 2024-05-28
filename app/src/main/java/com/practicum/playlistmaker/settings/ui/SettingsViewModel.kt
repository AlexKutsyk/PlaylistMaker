package com.practicum.playlistmaker.settings.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val providerSettings: SettingsInteractor,
    private val providerSharing: SharingInteractor,
) : ViewModel() {

    private var stateThemeLiveDada = MutableLiveData<Boolean>()

    init {
        stateThemeLiveDada.value = getSettings()
    }

    fun getStateThemeLiveData(): LiveData<Boolean> = stateThemeLiveDada

    private fun getSettings(): Boolean {
        return providerSettings.getSettings()
    }

    fun saveSettings() {
        providerSettings.saveSettings(stateThemeLiveDada.value!!)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        stateThemeLiveDada.value = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    fun shareApp(link: String) {
        providerSharing.shareApp(link)
    }

    fun openTerms() {
        providerSharing.openTerm()
    }

    fun openSupport() {
        providerSharing.openSupport()
    }

}