package com.example.pokeapp.data.data_source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SummaryPokemonResponse(val name: String, val url: String)