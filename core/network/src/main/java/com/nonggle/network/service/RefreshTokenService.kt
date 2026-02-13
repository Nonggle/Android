package com.nonggle.network.service

import com.nonggle.network.di.AuthClient
import com.nonggle.network.model.TokenRequest
import com.nonggle.network.model.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Singleton

interface RefreshTokenService {
    suspend fun refresh(refreshToken: String): TokenResponse
}

@Singleton
class KtorRefreshTokenService @Inject constructor(
    @AuthClient private val authClient: HttpClient,
) : RefreshTokenService {
    override suspend fun refresh(refreshToken: String): TokenResponse {
        return authClient.post("/auth/refresh") {
            setBody(TokenRequest(refreshToken = refreshToken))
        }.body()
    }
}
