package com.example.pokeapp.data.data_source.local

import com.example.pokeapp.data.data_source.local.model.Pokemon
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class PokemonLDS (private val realm: Realm) {
    fun getPokemons() : List<Pokemon> {
        return realm.query<Pokemon>().find()
    }

    suspend fun insertPokemon(pokemon: Pokemon) {
        realm.write { copyToRealm(pokemon) }
    }

    suspend fun clearPokemons() {
        realm.write { deleteAll() }
    }
}