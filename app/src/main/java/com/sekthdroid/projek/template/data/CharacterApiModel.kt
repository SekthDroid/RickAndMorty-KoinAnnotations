package com.sekthdroid.projek.template.data

import kotlinx.serialization.Serializable

@Serializable
data class CharacterApiModel(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationApiModel,
    val name: String,
    val origin: OriginApiModel,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)