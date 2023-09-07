package com.example.pokeapp.data.data_source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GetPokemonListResponse (
    val next: String?,
    val count: Int,
    val previous: String?,
    val results: List<SummaryPokemonResponse>
)