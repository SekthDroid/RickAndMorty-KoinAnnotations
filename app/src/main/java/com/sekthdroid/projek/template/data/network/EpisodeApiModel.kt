package com.sekthdroid.projek.template.data.network

import com.sekthdroid.projek.template.domain.model.Episode
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeApiModel(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)

fun EpisodeApiModel.toEpisode(): Episode {
    return Episode(id, name, episode)
}