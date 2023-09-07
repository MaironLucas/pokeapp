package com.example.mymovies.domain.use_case

import androidx.paging.PagingData
import com.example.mymovies.data.model.PokemonSummaryRM
import com.example.mymovies.data.repository.PokeRepository
import com.example.mymovies.domain.model.PokemonSummary
import kotlinx.coroutines.flow.Flow

class GetPokemonListUC (private val pokeRepository: PokeRepository) {
    operator fun invoke(): Flow<PagingData<PokemonSummaryRM>> {
        return pokeRepository.getPokemons()
    }
}