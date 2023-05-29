package com.sekthdroid.projek.template.data.network

import kotlinx.serialization.Serializable

@Serializable
data class OriginApiModel(
    val name: String,
    val url: String
)