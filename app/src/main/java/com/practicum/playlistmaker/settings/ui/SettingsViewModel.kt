package com.practicum.playlistmaker.settings.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.sharing.domain.SharingInteractor
import com.practicum.playlistmaker.util.Creator

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

    fun shareApp() {
        providerSharing.shareApp()
    }

    fun openTerms() {
        providerSharing.openTerm()
    }

    fun openSupport() {
        providerSharing.openSupport()
    }

    companion object {
        fun getViewModelFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val providerSettings = Creator.provideSettingsInteractor(context)
                val providerSharing = Creator.provideSharingInteractor(context)
                SettingsViewModel(
                    providerSettings,
                    providerSharing,
                )
            }
        }
    }
}