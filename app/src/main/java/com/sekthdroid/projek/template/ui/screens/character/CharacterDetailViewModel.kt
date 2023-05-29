package com.sekthdroid.projek.template.ui.screens.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class CharacterDetailViewModel(
    private val repository: CharactersRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterScreenState())
    val state: StateFlow<CharacterScreenState>
        get() = _state

    private val characterId: Int
        get() = savedStateHandle.get<Int>("id") ?: -1

    init {
        viewModelScope.launch {
            val character = repository.getCharacter(characterId)
            _state.update {
                it.copy(character = character)
            }

            val episodes = repository.getEpisodes(characterId)
            _state.update {
                it.copy(episodes = episodes)
            }
        }
    }
}