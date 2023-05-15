package com.sekthdroid.projek.template.data.sql

import com.sekthdroid.Database
import com.sekthdroid.projek.template.domain.model.Episode
import com.sekthdroid.projek.template.domain.model.SerieCharacter
import com.sekthdroid.sqldelight.SerieCharacters

class LocalDatasource(private val database: Database) {
    fun getCharacters(): List<SerieCharacter> {
        return database.serieCharactersQueries.getAll()
            .executeAsList()
            .map {
                SerieCharacter(
                    it.id.toInt(), it.name, it.image, it.location, it.origin
                )
            }
    }

    fun create(vararg character: SerieCharacter) {
        database.transaction {
            character.forEach { each ->
                database.serieCharactersQueries.create(
                    SerieCharacters(
                        each.id.toLong(),
                        each.name,
                        each.image,
                        each.location,
                        each.origin
                    )
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
            .executeAsOneOrNull()
            ?.let {
                SerieCharacter(
                    it.id.toInt(), it.name, it.image, it.location, it.origin
                )
            }
    }

    fun getEpisodes(characterId: Int): List<Episode> {
        return database.episodesQueries.getFromCharacter(characterId.toLong())
            .executeAsList()
            .map {
                Episode(it.id.toInt(), it.name, it.episode)
            }
    }
}