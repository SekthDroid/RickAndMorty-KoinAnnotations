package com.sekthdroid.projek.template.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sekthdroid.projek.template.data.CharacterApiModel
import com.sekthdroid.projek.template.data.network.RestDatasource
import com.sekthdroid.projek.template.di.Injector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CharactersScreenState(
    val items: List<CharacterApiModel> = emptyList(),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)

class CharactersViewModel(
    private val datasource: RestDatasource
) : ViewModel() {
    private val _state = MutableStateFlow(CharactersScreenState())
    val state: StateFlow<CharactersScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            val characters = datasource.getCharacters()
            _state.update {
                it.copy(isLoading = false, items = it.items.plus(characters))
            }
        }
    }

    fun fetchMore() {
        viewModelScope.launch {
            val currentPage = _state.value.items.size / 20 + 1
            val characters = datasource.getCharacters(currentPage)
            println("fetchMore with page $currentPage")
            _state.update {
                it.copy(isLoading = false, items = it.items.plus(characters))
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CharactersViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(Injector.restDatasource) as T
        }
        throw IllegalArgumentException()
    }
}