package com.example.pokeapp.domain.repository

import androidx.paging.PagingData
import com.example.pokeapp.domain.model.PokemonSummary
import kotlinx.coroutines.flow.Flow

interface PokeDataRepository {
    fun getPokemons(query: String): Flow<PagingData<PokemonSummary>>
}