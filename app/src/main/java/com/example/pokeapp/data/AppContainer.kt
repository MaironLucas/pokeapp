package com.example.pokeapp.data

import com.example.pokeapp.data.data_source.local.PokemonLDS
import com.example.pokeapp.data.data_source.local.model.Pokemon
import com.example.pokeapp.data.data_source.remote.PokemonRDS
import com.example.pokeapp.data.repository.PokeRepository
import com.example.pokeapp.domain.use_case.GetPokemonListUC
import com.example.pokeapp.network.PokeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val getPokemonListUC: GetPokemonListUC
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://pokeapi.co/api/v2/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val pokeApiService: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }

    private val realm: Realm by lazy {
        Realm.open(
            RealmConfiguration.Builder(
            schema = setOf(
                Pokemon::class
            )
        )
            .compactOnLaunch()
            .build())
    }

    private val pokemonRDS: PokemonRDS by lazy {
        PokemonRDS(pokeApiService)
    }

    private val pokemonLDS: PokemonLDS by lazy {
        PokemonLDS(realm)
    }

    private val pokemonRepository: PokeRepository by lazy {
        PokeRepository(pokemonRDS)
    }

    override val getPokemonListUC: GetPokemonListUC by lazy {
        GetPokemonListUC(pokemonRepository)
    }
}