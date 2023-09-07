package com.example.mymovies.domain.repository

import androidx.paging.PagingData
import com.example.mymovies.data.model.PokemonSummaryRM
import kotlinx.coroutines.flow.Flow

interface PokeDataRepository {
    fun getPokemons(): Flow<PagingData<PokemonSummaryRM>>
}