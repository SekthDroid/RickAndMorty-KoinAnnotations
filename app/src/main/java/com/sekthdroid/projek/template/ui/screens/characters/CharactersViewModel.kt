package com.sekthdroid.projek.template.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sekthdroid.projek.template.di.Injector
import com.sekthdroid.projek.template.domain.CharactersRepository
import com.sekthdroid.projek.template.domain.model.SerieCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CharactersScreenState(
    val items: List<SerieCharacter> = emptyList(),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)

class CharactersViewModel(
    private val repository: CharactersRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CharactersScreenState())
    val state: StateFlow<CharactersScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            val characters = repository.getCharacters()
            _state.update {
                it.copy(isLoading = false, items = it.items.plus(characters))
            }
        }
    }

    fun fetchMore() {
        viewModelScope.launch {
            val currentPage = _state.value.items.size / 20 + 1
            val characters = repository.getCharacters(currentPage)
            _state.update {
                it.copy(isLoading = false, items = it.items.plus(characters))
            }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CharactersViewModel(
                    Injector.charactersRepository
                ) as T
            }
        }
    }
}