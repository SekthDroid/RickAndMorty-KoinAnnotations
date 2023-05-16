package com.sekthdroid.projek.template.data.sql

import com.sekthdroid.projek.template.domain.model.Episode
import com.sekthdroid.projek.template.domain.model.SerieCharacter
import com.sekthdroid.projekt.data.Database

class SqliteDatasource(private val database: Database) {
    fun getCharacters(): List<SerieCharacter> {
        return database.serieCharactersQueries.getAll()
            .executeAsList()
            .map { it.toSerieCharacter() }
    }

    fun create(vararg character: SerieCharacter) {
        database.transaction {
            character.forEach { each ->
                database.serieCharactersQueries.create(
                    each.toSerieCharacterDataModel()
                )
            }
        }
    }

    fun create(characterId: Int, episodes: List<Episode>) {
        database.transaction {
            database.episodesQueries.deleteEpisode(characterId.toLong(), characterId.toLong())
            episodes.forEach { each ->
                database.episodesQueries.createEpisode(
                    each.id.toLong(),
                    each.name,
                    each.episode,
                    characterId.toLong(),
                    each.id.toLong()
                )
            }
        }
    }

    fun getCharacter(id: Int): SerieCharacter? {
        return database.serieCharactersQueries.getById(id.toLong())
            .executeAsOneOrNull()?.toSerieCharacter()
    }

    fun getEpisodes(characterId: Int): List<Episode> {
        return database.episodesQueries.getFromCharacter(characterId.toLong())
            .executeAsList()
            .map { it.toEpisode() }
    }
}