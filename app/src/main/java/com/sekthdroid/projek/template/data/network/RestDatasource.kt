package com.sekthdroid.projek.template.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments

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

    suspend fun getCharacter(value: Int) : CharacterApiModel? {
        val url = URLBuilder("https://rickandmortyapi.com/api/character")
            .appendPathSegments(value.toString())
            .build()

        return client.get(url).body()
    }

    suspend fun getEpisodes(vararg ids: Int) : List<EpisodeApiModel> {
        val url = URLBuilder("https://rickandmortyapi.com/api/episode")
            .appendPathSegments("[${ids.joinToString()}]")
            .build()

        return client.get(url).body()
    }
}