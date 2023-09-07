package com.example.pokeapp.domain.repository

import androidx.paging.PagingData
import com.example.pokeapp.data.data_source.remote.model.PokemonSummaryRM
import kotlinx.coroutines.flow.Flow

interface PokeDataRepository {
    fun getPokemons(): Flow<PagingData<PokemonSummaryRM>>
}