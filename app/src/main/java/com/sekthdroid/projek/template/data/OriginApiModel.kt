package com.sekthdroid.projek.template.data

import kotlinx.serialization.Serializable

@Serializable
data class OriginApiModel(
    val name: String,
    val url: String
)