package com.nonggle.network.di

import com.nonggle.auth.di.TokenManager
import com.nonggle.network.ApiClient
import com.nonggle.network.AuthClient
import com.nonggle.network.HttpClientFactory
import com.nonggle.network.KtorRefreshTokenService
import com.nonggle.network.RefreshTokenService
import com.nonggle.network.util.AuthEvent
import com.nonggle.network.util.AuthEventBus
import com.nonggle.network.util.DefaultAuthEventBus
import com.nonggle.network.util.NetworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.http.encodedPath
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthEventBus(): AuthEventBus = DefaultAuthEventBus()

    @Provides
    @Singleton
    @AuthClient
    fun provideAuthHttpClient(): HttpClient {
        return HttpClientFactory.createBaseClient(
            baseUrl = NetworkConfig.baseUrl,
            timeoutMs = NetworkConfig.TIMEOUT_MS,
            loggerTag = "KtorAuthLogger"
        )
    }

    @Provides
    @Singleton
    fun provideRefreshTokenService(
        @AuthClient authClient: HttpClient
    ): RefreshTokenService = KtorRefreshTokenService(authClient)

    @Provides
    @Singleton
    @ApiClient
    fun provideApiHttpClient(
        tokenManager: TokenManager,
        refreshTokenService: RefreshTokenService,
        authEventBus: AuthEventBus,
    ): HttpClient {
        val refreshMutex = Mutex()

        return HttpClientFactory.createBaseClient(
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
}
