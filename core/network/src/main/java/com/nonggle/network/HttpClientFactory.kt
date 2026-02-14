package com.nonggle.network

import io.ktor.client.plugins.logging.Logger
import android.util.Log
import com.example.common.result.AuthEvent
import com.example.common.result.AuthEventBus
import com.nonggle.auth.di.TokenManager
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
            install(Auth) {
                bearer {
                    loadTokens {
                        val access = tokenManager.getAccessToken()
                        val refresh = tokenManager.getRefreshToken()
                        if (access != null && refresh != null) BearerTokens(
                            access,
                            refresh
                        ) else null
                    }

                    // 토큰 갱신은 "AuthClient 기반 RefreshTokenService"에 위임
                    refreshTokens {
                        refreshMutex.withLock {
                            val refreshToken = tokenManager.getRefreshToken()
                                ?: run {
                                    authEventBus.emit(AuthEvent.SessionExpired)
                                    return@withLock null
                                }

                            val result = runCatching {
                                refreshTokenService.refresh(refreshToken)
                            }

                            return@withLock result.fold(
                                onSuccess = { tokenResponse ->
                                    tokenManager.saveTokens(
                                        tokenResponse.accessToken,
                                        tokenResponse.refreshToken
                                    )
                                    BearerTokens(tokenResponse.accessToken, tokenResponse.refreshToken)
                                },
                                onFailure = {
                                    tokenManager.deleteToken()
                                    authEventBus.emit(AuthEvent.SessionExpired)
                                    null
                                }
                            )
                        }
                    }

                    sendWithoutRequest { request ->
                        request.url.encodedPath in setOf(
                            "/auth/kakao",
                        )
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
