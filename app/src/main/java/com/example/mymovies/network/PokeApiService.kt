package com.example.mymovies.network

import com.example.mymovies.data.model.GetPokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemons(@Query("offset") offset: Int): GetPokemonListResponse
}