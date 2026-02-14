package com.nonggle.network.service

import com.nonggle.model.AppResult
import com.nonggle.network.di.AuthClient
import com.nonggle.network.model.auth.RefreshTokenRequestDto
import com.nonggle.network.model.auth.TokenResponseDto
import com.nonggle.network.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Singleton

interface AuthService {
    suspend fun refresh(refreshToken: String): AppResult<TokenResponseDto>
}

@Singleton
class KtorRefreshTokenService @Inject constructor(
    @AuthClient private val authClient: HttpClient,
) : AuthService {
    override suspend fun refresh(refreshToken: String): AppResult<TokenResponseDto> {
        return safeApiCall<TokenResponseDto> {
            authClient.post("/auth/token/refresh") {
                setBody(RefreshTokenRequestDto(refreshToken = refreshToken))
            }
        }
    }

}
