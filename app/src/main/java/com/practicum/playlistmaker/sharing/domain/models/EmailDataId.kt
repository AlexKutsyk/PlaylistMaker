package com.practicum.playlistmaker.sharing.domain.models

import androidx.annotation.StringRes

data class EmailDataId(
    @StringRes val idAdress: Int,
    @StringRes val idSubject: Int,
    @StringRes val idMessage: Int,
) {
}