package com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi

import com.example.coinmandi.core.sensitive.GekoRoutes
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {
    operator fun invoke(engine : HttpClientEngine) : HttpClient = HttpClient(engine){
        install(ContentNegotiation){
            json(
                json = Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging){
            level = LogLevel.ALL
            logger = Logger.ANDROID
            // Just Check if both are Getting Logged
            logger = object  : Logger {
                override fun log(message: String) {
                    println("MYCUSTOMLOG : $message")
                }
            }
        }
        defaultRequest {
             url(GekoRoutes.baseurl)
             contentType(ContentType.Application.Json)
             header(GekoRoutes.apikey , GekoRoutes.keyvalue)
        }
    }
}