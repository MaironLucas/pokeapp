package com.example.pokeapp

import android.app.Application
import com.example.pokeapp.data.AppContainer
import com.example.pokeapp.data.DefaultAppContainer

class PokeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}