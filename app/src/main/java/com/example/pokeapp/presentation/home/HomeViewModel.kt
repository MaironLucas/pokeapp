package com.example.pokeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.cachedIn
import com.example.pokeapp.PokeApplication
import com.example.pokeapp.domain.use_case.GetPokemonListUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel(private val getPokemonListUC: GetPokemonListUC) : ViewModel() {

    private val _search = MutableStateFlow("")

    val search = _search.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    private val _isSearchShowing = MutableStateFlow(false)

    val isSearchShowing = _isSearchShowing.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false,
        )

    val pokeListPaging = search.debounce(300.milliseconds)
        .flatMapLatest { query -> getPokemonListUC(query).cachedIn(viewModelScope) }

    fun setSearch(query: String) {
        _search.value = query
    }

    fun toggleSearch() {
        _isSearchShowing.value = !_isSearchShowing.value
    }

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