package com.example.pokeapp.data.data_source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokeapp.data.data_source.remote.model.PokemonSummaryRM
import com.example.pokeapp.network.PokeApiService
import retrofit2.HttpException
import java.io.IOException

class PokemonRDS(private val pokeApiService: PokeApiService) :
    PagingSource<Int, PokemonSummaryRM>() {
    suspend fun getPokemons(offset: Int, limit: Int): List<PokemonSummaryRM> {
        return pokeApiService.getPokemons(offset, limit).results.map { pokemon ->
            val id = pokemon.url.split("/").last { it.isNotBlank() }.toInt()
            PokemonSummaryRM(
                pokemon.name,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/$id.svg",
                id
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonSummaryRM>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonSummaryRM> {
        val position = params.key ?: 0
        return try {
            val response = pokeApiService.getPokemons(position * 20, 20)
            LoadResult.Page(
                data = response.results.map { pokemon ->
                    val id = pokemon.url.split("/").last { it.isNotBlank() }.toInt()
                    PokemonSummaryRM(
                        pokemon.name,
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/$id.svg",
                        id
                    )
                },
                nextKey = if (response.next != null) position + 1 else null,
                prevKey = if (position > 0) position - 1 else null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}