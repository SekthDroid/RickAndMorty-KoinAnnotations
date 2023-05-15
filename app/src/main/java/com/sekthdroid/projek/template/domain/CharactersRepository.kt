package com.sekthdroid.projek.template.domain

import com.sekthdroid.projek.template.data.memory.MemoryDatasource
import com.sekthdroid.projek.template.data.network.RestDatasource
import com.sekthdroid.projek.template.data.network.toCharacter
import com.sekthdroid.projek.template.data.network.toEpisode
import com.sekthdroid.projek.template.domain.model.Episode
import com.sekthdroid.projek.template.domain.model.SerieCharacter

class CharactersRepository(
    private val networkDatasource: RestDatasource,
    private val memoryDatasource: MemoryDatasource
) {
    suspend fun getCharacters(page: Int = 0): List<SerieCharacter> {
        val apiPage = if (page == 1) 0 else page

        val memory = memoryDatasource.getCharacters().chunked(20)
        val candidates = memory.getOrNull(apiPage).orEmpty()
        if (candidates.isNotEmpty()) {
            return candidates
        }

        return networkDatasource.getCharacters(apiPage)
            .map { it.toCharacter() }
            .also { memoryDatasource.put(it) }
    }

    suspend fun getCharacter(character: Int): SerieCharacter? {
        return memoryDatasource.getCharacters().find { it.id == character }
    }

    suspend fun getEpisodes(characterId: Int): List<Episode> {
        val episodes = memoryDatasource.getEpisodes(characterId)
        if (episodes.isEmpty()) {
            val episodeIds = networkDatasource.getCharacter(characterId)
                ?.episode.orEmpty()
                .map { it.split("/").last().toInt() }
                .toIntArray()

            return networkDatasource.getEpisodes(*episodeIds)
                .map { it.toEpisode() }
                .also { memoryDatasource.put(characterId, it) }
        }
        return episodes
    }
}