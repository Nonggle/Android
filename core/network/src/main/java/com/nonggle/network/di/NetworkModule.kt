package com.nonggle.network.di

import com.example.common.result.AuthEventBus
import com.example.common.result.DefaultAuthEventBus
import com.nonggle.auth.di.TokenManager
import com.nonggle.network.HttpClientFactory
import com.nonggle.network.service.AuthService
import com.nonggle.network.service.KtorRefreshTokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthClient

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
        return HttpClientFactory.createAuthHttpClient()
    }

    @Provides
    @Singleton
    fun provideRefreshTokenService(
        @AuthClient authClient: HttpClient,
        @ApiClient apiClient: HttpClient
    ): AuthService = KtorRefreshTokenService(authClient, apiClient)

    @Provides
    @Singleton
    @ApiClient
    fun provideApiClient(
        tokenManager: TokenManager,
        refreshTokenService: AuthService,
        authEventBus: AuthEventBus,
    ): HttpClient {
        return HttpClientFactory.createApiClient(tokenManager, refreshTokenService, authEventBus)
    }
}
