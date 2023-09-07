package com.example.pokeapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokeapp.data.data_source.remote.PokemonRDS
import com.example.pokeapp.data.data_source.remote.model.PokemonSummaryRM
import com.example.pokeapp.domain.repository.PokeDataRepository
import kotlinx.coroutines.flow.Flow

class PokeRepository(private val pokemonRDS: PokemonRDS) : PokeDataRepository {
    override fun getPokemons() : Flow<PagingData<PokemonSummaryRM>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        ){pokemonRDS}.flow
    }
}