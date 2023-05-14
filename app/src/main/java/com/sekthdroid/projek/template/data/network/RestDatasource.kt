package com.sekthdroid.projek.template.data.network

import com.sekthdroid.projek.template.data.ApiResponse
import com.sekthdroid.projek.template.data.CharacterApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder

class RestDatasource(
    private val client: HttpClient
) {

    suspend fun getCharacters(page: Int = 0): List<CharacterApiModel> {
        val url = URLBuilder("https://rickandmortyapi.com/api/character")
            .apply {
                if (page != 0) {
                    encodedParameters = ParametersBuilder().apply {
                        append("page", page.toString())
                    }
                }
            }
            .build()

        val response = client.get(url).body<ApiResponse>()
        return response.results
    }
}