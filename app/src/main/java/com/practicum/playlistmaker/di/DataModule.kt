package com.practicum.playlistmaker.di

import androidx.room.Room
import com.practicum.playlistmaker.library.favorites.data.db.AppDataBase
import com.practicum.playlistmaker.search.data.NetworkClient
import com.practicum.playlistmaker.search.data.network.ITunesAPI
import com.practicum.playlistmaker.search.data.network.RetrofitNetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<ITunesAPI> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesAPI::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(
            androidContext(),
            get()
        )
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            "database.db"
        ).build()
    }

}