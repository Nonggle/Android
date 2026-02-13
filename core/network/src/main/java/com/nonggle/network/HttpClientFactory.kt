package com.nonggle.network

import io.ktor.client.plugins.logging.Logger
import android.util.Log
import com.nonggle.network.util.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    fun createBaseClient(
        baseUrl: String,
        timeoutMs: Long,
        loggerTag: String,
        json: Json = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        },
    ): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) { json(json) }

            install(HttpTimeout) {
                requestTimeoutMillis = timeoutMs
                connectTimeoutMillis = timeoutMs
                socketTimeoutMillis = timeoutMs
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v(loggerTag, message)
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                url(baseUrl)
                header("App-Version", NetworkConfig.APP_VERSION)
                contentType(ContentType.Application.Json)
            }
        }
    }
}
