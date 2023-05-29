package com.sekthdroid.projek.template.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

enum class Endpoints(val path: String) {
    Characters("character"),
    Episodes("episode");

    fun build(baseUrl: String): String {
        return "$baseUrl/$path"
    }
}

@Single
class RestDatasource(
    private val client: HttpClient,
    @Named("base_url") private val baseUrl: String = "https://rickandmortyapi.com/api"
) {

    suspend fun getCharacters(page: Int = 0): List<CharacterApiModel> {
        val url = URLBuilder(Endpoints.Characters.build(baseUrl))
            .apply {
                if (page != 0) {
                    encodedParameters = ParametersBuilder().apply {
                        append("page", page.toString())
                    }
                }
            }
            .build()

        val response = client.get(url).body<PagedApiResponse>()
        return response.results
    }

    suspend fun getCharacter(value: Int): CharacterApiModel? {
        val url = URLBuilder(Endpoints.Characters.build(baseUrl))
            .appendPathSegments(value.toString())
            .build()

        return client.get(url).body()
    }

    suspend fun getEpisodes(vararg ids: Int): List<EpisodeApiModel> {
        val url = URLBuilder(Endpoints.Episodes.build(baseUrl))
            .appendPathSegments("[${ids.joinToString()}]")
            .build()

        return client.get(url).body()
    }
}