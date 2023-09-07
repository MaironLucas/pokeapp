package com.example.pokeapp.network

import com.example.pokeapp.data.data_source.remote.model.GetPokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemons(@Query("offset") offset: Int, @Query("limit") limit: Int): GetPokemonListResponse
}