package com.example.pokeapp.domain.use_case

import androidx.paging.PagingData
import com.example.pokeapp.data.data_source.remote.model.PokemonSummaryRM
import com.example.pokeapp.data.repository.PokeRepository
import com.example.pokeapp.domain.model.PokemonSummary
import kotlinx.coroutines.flow.Flow

class GetPokemonListUC (private val pokeRepository: PokeRepository) {
    operator fun invoke(): Flow<PagingData<PokemonSummaryRM>> {
        return pokeRepository.getPokemons()
    }
}