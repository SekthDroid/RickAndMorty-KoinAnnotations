package com.sekthdroid.projek.template.data.memory

import com.sekthdroid.projek.template.domain.model.Episode
import com.sekthdroid.projek.template.domain.model.SerieCharacter

class MemoryDatasource {
    private val charactersList = mutableListOf<SerieCharacter>()
    private val episodesMap = mutableMapOf<Int, List<Episode>>()

    fun put(characters: List<SerieCharacter>) {
        charactersList.addAll(characters)
    }

    fun getCharacters(): List<SerieCharacter> {
        return charactersList.toList()
    }

    fun put(character: Int, episodes: List<Episode>) {
        episodesMap[character] = episodes
    }

    fun getEpisodes(character: Int): List<Episode> {
        return episodesMap[character].orEmpty()
    }
}