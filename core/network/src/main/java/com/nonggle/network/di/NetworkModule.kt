package com.nonggle.network.di

import com.example.common.result.AuthEventBus
import com.nonggle.auth.di.TokenManager
import com.nonggle.network.HttpClientFactory
import com.nonggle.network.service.AuthService
import com.nonggle.network.service.KtorRefreshTokenService
import com.nonggle.network.service.LoginService
import com.nonggle.network.service.LoginServiceImpl
import com.nonggle.network.service.ResumeService
import com.nonggle.network.service.ResumeServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton
import com.nonggle.common.network.IoDispatcher

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
    @AuthClient
    fun provideAuthHttpClient(): HttpClient {
        return HttpClientFactory.createAuthHttpClient()
    }

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

    @Provides
    @Singleton
    fun provideRefreshTokenService(
        @AuthClient authClient: HttpClient,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AuthService = KtorRefreshTokenService(authClient, ioDispatcher)

    @Provides
    @Singleton
    fun provideLoginService(
        @ApiClient baseClient: HttpClient,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): LoginService = LoginServiceImpl(baseClient, ioDispatcher)

    @Provides
    @Singleton
    fun provideResumeService(
        @ApiClient baseClient: HttpClient,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ResumeService = ResumeServiceImpl(baseClient, ioDispatcher)
}
