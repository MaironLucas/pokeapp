package com.example.mymovies.data.model

import com.example.mymovies.domain.model.PokemonSummary

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