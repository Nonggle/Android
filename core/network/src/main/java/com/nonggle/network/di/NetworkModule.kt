package com.nonggle.network.di

import com.nonggle.auth.di.TokenManager
import com.nonggle.network.HttpClientFactory
import com.nonggle.network.service.KtorRefreshTokenService
import com.nonggle.network.service.RefreshTokenService
import com.nonggle.network.util.AuthEventBus
import com.nonggle.network.util.DefaultAuthEventBus
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
        @AuthClient authClient: HttpClient
    ): RefreshTokenService = KtorRefreshTokenService(authClient)

    @Provides
    @Singleton
    @ApiClient
    fun provideApiClient(
        tokenManager: TokenManager,
        refreshTokenService: RefreshTokenService,
        authEventBus: AuthEventBus,
    ): HttpClient {
        return HttpClientFactory.createApiClient(tokenManager, refreshTokenService, authEventBus)
    }
}
