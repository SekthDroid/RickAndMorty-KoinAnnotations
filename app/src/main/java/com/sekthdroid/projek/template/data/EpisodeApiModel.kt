package com.sekthdroid.projek.template.data

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