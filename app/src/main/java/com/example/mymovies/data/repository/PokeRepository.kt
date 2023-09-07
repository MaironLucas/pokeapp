package com.example.mymovies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mymovies.data.data_source.PokemonRDS
import com.example.mymovies.data.model.PokemonSummaryRM
import com.example.mymovies.domain.repository.PokeDataRepository
import kotlinx.coroutines.flow.Flow

class PokeRepository(private val pokemonRDS: PokemonRDS) : PokeDataRepository {
    override fun getPokemons() : Flow<PagingData<PokemonSummaryRM>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        ){pokemonRDS}.flow
    }
}