package com.example.pokeapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokeapp.data.Paginator
import com.example.pokeapp.data.data_source.remote.PokemonRDS
import com.example.pokeapp.domain.model.PokemonSummary
import com.example.pokeapp.domain.repository.PokeDataRepository
import kotlinx.coroutines.flow.Flow

class PokeRepository(private val pokemonRDS: PokemonRDS) : PokeDataRepository {
    override fun getPokemons(query: String) : Flow<PagingData<PokemonSummary>> {

        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        ){
            Paginator(pokemonRDS,query)}.flow
    }

}