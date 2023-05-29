package com.sekthdroid.projek.template.domain

import com.sekthdroid.projek.template.data.network.RestDatasource
import com.sekthdroid.projek.template.data.network.toCharacter
import com.sekthdroid.projek.template.data.network.toEpisode
import com.sekthdroid.projek.template.data.sql.SqliteDatasource
import com.sekthdroid.projek.template.domain.model.Episode
import com.sekthdroid.projek.template.domain.model.SerieCharacter
import org.koin.core.annotation.Single

@Single
class CharactersRepository(
    private val networkDatasource: RestDatasource,
    private val sqliteDatasource: SqliteDatasource
) {
    suspend fun getCharacters(page: Int = 0): List<SerieCharacter> {
        val apiPage = if (page == 1) 0 else page

        val memory = sqliteDatasource.getCharacters().chunked(20)
        println("In database ${memory.size}")
        val candidates = memory.getOrNull(apiPage).orEmpty()
        if (candidates.isNotEmpty()) {
            return candidates
        }

        return networkDatasource.getCharacters(apiPage)
            .map { it.toCharacter() }
            .also { sqliteDatasource.create(*it.toTypedArray()) }
    }

    suspend fun getCharacter(character: Int): SerieCharacter? {
        return sqliteDatasource.getCharacter(character)
    }

    suspend fun getEpisodes(characterId: Int): List<Episode> {
        val episodes = sqliteDatasource.getEpisodes(characterId)
        if (episodes.isEmpty()) {
            val episodeIds = networkDatasource.getCharacter(characterId)
                ?.episode.orEmpty()
                .map { it.split("/").last().toInt() }
                .toIntArray()

            return networkDatasource.getEpisodes(*episodeIds)
                .map { it.toEpisode() }
                .also { sqliteDatasource.create(characterId, it) }
        }
        return episodes
    }
}