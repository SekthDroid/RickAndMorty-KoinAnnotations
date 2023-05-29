package com.sekthdroid.projek.template.data

import android.util.Log
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sekthdroid.projek.template.data.network.RestDatasource
import com.sekthdroid.projek.template.data.sql.SqliteDatasource
import com.sekthdroid.projek.template.domain.CharactersRepository
import com.sekthdroid.projekt.data.Database
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i(Logging.key.name, message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    single(named("base_url")) { "https://rickandmortyapi.com/api" }

    single {
        RestDatasource(get())
    }

    single {
        Database(AndroidSqliteDriver(Database.Schema, androidApplication(), "app.db"))
    }

    single {
        SqliteDatasource(get())
    }

    single {
        CharactersRepository(get(), get())
    }
}