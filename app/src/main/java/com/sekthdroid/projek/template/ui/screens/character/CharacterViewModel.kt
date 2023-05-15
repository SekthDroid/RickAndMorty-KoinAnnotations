package com.sekthdroid.projek.template.ui.screens.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sekthdroid.projek.template.di.Injector
import com.sekthdroid.projek.template.domain.CharactersRepository
import com.sekthdroid.projek.template.domain.model.Episode
import com.sekthdroid.projek.template.domain.model.SerieCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CharacterScreenState(
    val isLoading: Boolean = false,
    val character: SerieCharacter? = null,
    val episodes: List<Episode> = emptyList()
)

class CharacterViewModel(
    private val repository: CharactersRepository,
    private val id: Int? = null
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterScreenState())
    val state: StateFlow<CharacterScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            val character = repository.getCharacter(id ?: -1)
            _state.update {
                it.copy(character = character)
            }

            val episodes = repository.getEpisodes(id ?: 0)
            _state.update {
                it.copy(episodes = episodes)
            }
        }
    }
}

class CharacterViewModelFactory(private val id: Int?) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterViewModel(Injector.charactersRepository, id) as T
    }
}