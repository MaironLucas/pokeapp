package com.example.mymovies

import android.app.Application
import com.example.mymovies.data.AppContainer
import com.example.mymovies.data.DefaultAppContainer

class PokeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}