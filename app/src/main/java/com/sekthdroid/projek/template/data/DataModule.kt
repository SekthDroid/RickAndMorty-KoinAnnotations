package com.sekthdroid.projek.template.data

import android.app.Application
import android.util.Log
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sekthdroid.projekt.data.Database
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@ComponentScan
class DataModule {

    @Single
    fun provideClient(): HttpClient {
        return HttpClient {
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

    @Single
    @Named("base_url")
    fun provideBaseUrl(): String = "https://rickandmortyapi.com/api"

    @Single
    fun provideDatabase(application: Application): Database {
        return Database(AndroidSqliteDriver(Database.Schema, application, "app.db"))
    }
}