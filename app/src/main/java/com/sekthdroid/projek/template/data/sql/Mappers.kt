package com.sekthdroid.projek.template.data.sql

import com.sekthdroid.projek.template.domain.model.Episode
import com.sekthdroid.projek.template.domain.model.SerieCharacter
import com.sekthdroid.sqldelight.Episodes
import com.sekthdroid.sqldelight.SerieCharacters

typealias SerieCharacterDataModel = SerieCharacters
typealias EpisodeDataModel = Episodes

fun SerieCharacterDataModel.toSerieCharacter() =
    SerieCharacter(id.toInt(), name, image, location, origin)

fun SerieCharacter.toSerieCharacterDataModel() = SerieCharacterDataModel(
    id.toLong(),
    name,
    image,
    location,
    origin
)

fun EpisodeDataModel.toEpisode() = Episode(id.toInt(), name, episode)