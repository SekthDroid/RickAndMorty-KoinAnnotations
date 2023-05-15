package com.sekthdroid.projek.template.data.network

import kotlinx.serialization.Serializable

@Serializable
data class LocationApiModel(
    val name: String,
    val url: String
)