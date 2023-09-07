package com.example.mymovies.data

import com.example.mymovies.data.data_source.PokemonRDS
import com.example.mymovies.data.repository.PokeRepository
import com.example.mymovies.domain.use_case.GetPokemonListUC
import com.example.mymovies.network.PokeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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

    private val pokemonRDS: PokemonRDS by lazy {
        PokemonRDS(pokeApiService)
    }

    private val pokemonRepository: PokeRepository by lazy {
        PokeRepository(pokemonRDS)
    }

    override val getPokemonListUC: GetPokemonListUC by lazy {
        GetPokemonListUC(pokemonRepository)
    }
}