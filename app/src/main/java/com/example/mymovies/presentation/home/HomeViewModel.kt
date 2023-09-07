package com.example.mymovies.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovies.PokeApplication
import com.example.mymovies.data.model.PokemonSummaryRM
import com.example.mymovies.domain.model.PokemonSummary
import com.example.mymovies.domain.use_case.GetPokemonListUC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

sealed interface PokeListUiState {
    data class Success(val pokeList: List<PokemonSummary>) : PokeListUiState
    data class Error(val errorMessage: String) : PokeListUiState
    object Loading : PokeListUiState
}

class HomeViewModel(private val getPokemonListUC: GetPokemonListUC) : ViewModel() {
    var pokeListUiState: PokeListUiState by mutableStateOf(PokeListUiState.Loading)
        private set

    val pokeListPaging = getPokemonListUC.invoke().cachedIn(viewModelScope)

//    init {
//        getPokemonList()
//    }
//
//    fun getPokemonList() {
//        viewModelScope.launch {
//            pokeListUiState = PokeListUiState.Loading
//            pokeListUiState = try {
//                PokeListUiState.Success(getPokemonListUC())
//            } catch (e: Exception){
//                PokeListUiState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokeApplication)
                val getPokemonListUC = application.container.getPokemonListUC
                HomeViewModel(getPokemonListUC = getPokemonListUC)
            }
        }
    }
}