package com.sekthdroid.projek.template.data.network

import kotlinx.serialization.Serializable

@Serializable
class ApiResponse(
    val info: PageInfoApiModel,
    val results: List<CharacterApiModel>
)