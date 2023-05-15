package com.sekthdroid.projek.template.ui.screens.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sekthdroid.projek.template.data.CharacterApiModel
import com.sekthdroid.projek.template.data.EpisodeApiModel
import com.sekthdroid.projek.template.data.network.RestDatasource
import com.sekthdroid.projek.template.di.Injector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CharacterScreenState(
    val isLoading: Boolean = false,
    val character: CharacterApiModel? = null,
    val episodes: List<EpisodeApiModel> = emptyList()
)

class CharacterViewModel(
    private val dataSource: RestDatasource,
    private val id: Int? = null
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterScreenState())
    val state: StateFlow<CharacterScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            val character = dataSource.getCharacter(id ?: -1)
            _state.update {
                it.copy(character = character)
            }

            val characterEpisodes = character?.episode.orEmpty()
                .map { it.split("/").last() }
                .map { it.toInt() }
                .toTypedArray()

            val episodes = dataSource.getEpisodes(*characterEpisodes.toIntArray())
            _state.update {
                it.copy(episodes = episodes)
            }
        }
    }
}

class CharacterViewModelFactory(private val id: Int?) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterViewModel(Injector.restDatasource, id) as T
    }
}