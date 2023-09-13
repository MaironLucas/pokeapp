package com.example.pokeapp.data.data_source.remote

import com.example.pokeapp.data.data_source.remote.model.GetPokemonListResponse
import com.example.pokeapp.network.PokeApiService

class PokemonRDS(private val pokeApiService: PokeApiService){
    suspend fun getPokemons(offset: Int, limit: Int): GetPokemonListResponse {
        return pokeApiService.getPokemons(offset, limit)
    }
}