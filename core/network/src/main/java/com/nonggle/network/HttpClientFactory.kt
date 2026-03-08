package com.nonggle.network

import io.ktor.client.plugins.logging.Logger
import android.util.Log
import com.nonggle.network.util.AuthEvent
import com.nonggle.network.util.AuthEventBus
import com.nonggle.auth.di.TokenManager
import com.nonggle.model.AppResult
import com.nonggle.network.service.AuthService
import com.nonggle.network.util.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json

object HttpClientFactory {

    // 1. 토큰 갱신용 클라이언트를 만드는 명시적인 함수
    fun createAuthHttpClient(): HttpClient {
        return createBaseClient(
            baseUrl = NetworkConfig.baseUrl,
            timeoutMs = NetworkConfig.TIMEOUT_MS,
            loggerTag = "KtorAuthLogger"
        )
    }

    fun createApiClient(
        tokenManager: TokenManager,
        refreshTokenService: AuthService,
        authEventBus: AuthEventBus,
    ): HttpClient {
        val refreshMutex = Mutex()

        return createBaseClient(
            baseUrl = NetworkConfig.baseUrl,
            timeoutMs = NetworkConfig.TIMEOUT_MS,
            loggerTag = "KtorLogger"
        ).config {
            expectSuccess = false
            install(Auth) {
                bearer {
                    cacheTokens = false
                    loadTokens {
                        val access = tokenManager.getAccessToken()
                        val refresh = tokenManager.getRefreshToken()
                        if (access != null && refresh != null) BearerTokens(
                            access,
                            refresh
                        ) else null
                    }

                    refreshTokens {
                        refreshMutex.withLock {
                            val refreshToken = tokenManager.getRefreshToken()

                            if(refreshToken == null) {
                                authEventBus.emit(AuthEvent.SessionExpired)
                                return@withLock null
                            }

                            val apiResult = refreshTokenService.refresh(refreshToken)

                            if(apiResult is AppResult.Success) {
                                val tokenResponse = apiResult.data

                                tokenManager.saveTokens(
                                    accessToken = tokenResponse?.accessToken ?: "",
                                    refreshToken = tokenResponse?.refreshToken ?: ""
                                )

                                BearerTokens(tokenResponse?.accessToken ?: "", tokenResponse?.refreshToken)
                            } else {
                                tokenManager.deleteToken()
                                authEventBus.emit(AuthEvent.SessionExpired)
                                return@withLock null
                            }

                        }
                    }

                    sendWithoutRequest { req ->
                        val path = req.url.encodedPath
                        path !in setOf("/auth/kakao", "/auth/token/refresh")
                    }
                }
            }
        }
    }

    private fun createBaseClient(
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
