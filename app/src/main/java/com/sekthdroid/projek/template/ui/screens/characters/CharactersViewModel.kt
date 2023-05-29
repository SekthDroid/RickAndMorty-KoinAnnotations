package com.sekthdroid.projek.template.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sekthdroid.projek.template.domain.CharactersRepository
import com.sekthdroid.projek.template.domain.model.SerieCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

data class CharactersScreenState(
    val items: List<SerieCharacter> = emptyList(),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)

@KoinViewModel
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

}