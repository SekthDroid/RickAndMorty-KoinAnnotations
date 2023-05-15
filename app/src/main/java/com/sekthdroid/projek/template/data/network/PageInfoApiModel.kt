package com.sekthdroid.projek.template.data.network

import kotlinx.serialization.Serializable

@Serializable
data class PageInfoApiModel(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)