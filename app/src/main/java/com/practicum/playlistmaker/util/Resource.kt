package com.practicum.playlistmaker.util

sealed class Resource<T>(
    val data: T? = null,
    val typeError: Int? = null,
) {
    class Success<T>(data: T) : Resource<T>(data, null)
    class Error<T>(typeError: Int) : Resource<T>(null, typeError)
}