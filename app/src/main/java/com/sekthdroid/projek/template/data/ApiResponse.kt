package com.sekthdroid.projek.template.data

import kotlinx.serialization.Serializable

@Serializable
class ApiResponse(
    val info: PageInfoApiModel,
    val results: List<CharacterApiModel>
)