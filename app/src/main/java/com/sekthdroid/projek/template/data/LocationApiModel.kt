package com.sekthdroid.projek.template.data

import kotlinx.serialization.Serializable

@Serializable
data class LocationApiModel(
    val name: String,
    val url: String
)