package com.sekthdroid.projek.template.di

import android.content.Context
import android.util.Log
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sekthdroid.Database
import com.sekthdroid.projek.template.data.memory.MemoryDatasource
import com.sekthdroid.projek.template.data.network.RestDatasource
import com.sekthdroid.projek.template.data.sql.LocalDatasource
import com.sekthdroid.projek.template.domain.CharactersRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.properties.Delegates

object Injector {
    private var appContext: Context by Delegates.notNull()

    fun init(context: Context) {
        this.appContext = context
    }

    val httpClient by lazy {
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

    val restDatasource by lazy {
        RestDatasource(httpClient)
    }

    val sqliteDatasource by lazy {
        LocalDatasource(database)
    }

    val charactersRepository by lazy {
        CharactersRepository(restDatasource, sqliteDatasource)
    }

    val database by lazy {
        Database(AndroidSqliteDriver(Database.Schema, appContext, "app.db"))
    }
}