package com.example.mymovies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetPokemonListResponse (
    val next: String?,
    val count: Int,
    val previous: String?,
    val results: List<SummaryPokemonResponse>
)