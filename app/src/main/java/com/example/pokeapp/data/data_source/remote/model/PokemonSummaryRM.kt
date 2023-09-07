package com.example.pokeapp.data.data_source.remote.model

import com.example.pokeapp.domain.model.PokemonSummary

class PokemonSummaryRM(
    val name: String,
    private val image: String,
    private val id: Int,
) {
    fun toDM() : PokemonSummary {
        return PokemonSummary(
            name,
            image,
            id,
        )
    }
}