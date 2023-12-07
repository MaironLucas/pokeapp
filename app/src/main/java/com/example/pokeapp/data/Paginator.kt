package com.example.pokeapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokeapp.data.data_source.remote.PokemonRDS
import com.example.pokeapp.domain.model.PokemonSummary
import retrofit2.HttpException
import java.io.IOException

class Paginator(private val pokemonRDS: PokemonRDS, private val searchString: String?) :
    PagingSource<Int, PokemonSummary>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonSummary>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonSummary> {
        val position = params.key ?: 0
        return try {
            val limit = if (searchString == null) params.loadSize else 100
            val response = pokemonRDS.getPokemons(position * if (searchString == null) 20 else 100, limit)
            var filteredData = if (searchString == null) {
                response.results.map { pokemon ->
                    val id = pokemon.url.split("/").last { it.isNotBlank() }.toInt()
                    PokemonSummary(
                        name = pokemon.name,
                        id = id,
                        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/$id.svg"
                    )
                }
            } else {
                response.results.filter {it.name.contains(searchString, true)}.map { pokemon ->
                    val id = pokemon.url.split("/").last { it.isNotBlank() }.toInt()
                    PokemonSummary(
                        name = pokemon.name,
                        id = id,
                        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/$id.svg"
                    )
                }
            }
            LoadResult.Page(
                data = filteredData,
                nextKey =  if (response.next == null) null else position + 1,
                prevKey = if (position > 0) position - 1 else null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}