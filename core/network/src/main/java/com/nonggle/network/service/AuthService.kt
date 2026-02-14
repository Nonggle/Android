package com.nonggle.network.service

import com.example.common.result.ApiResult
import com.nonggle.network.di.ApiClient
import com.nonggle.network.di.AuthClient
import com.nonggle.network.model.auth.RefreshTokenRequest
import com.nonggle.network.model.auth.TokenRequest
import com.nonggle.network.model.auth.TokenResponse
import com.nonggle.network.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Singleton

interface AuthService {
    suspend fun refresh(refreshToken: String): TokenResponse

    suspend fun kakaoLogin(accessToken: String): ApiResult<TokenResponse>
}

@Singleton
class KtorRefreshTokenService @Inject constructor(
    @AuthClient private val authClient: HttpClient,
    @ApiClient private val baseClient: HttpClient
) : AuthService {
    override suspend fun refresh(refreshToken: String): TokenResponse {
        return authClient.post("/auth/refresh") {
            setBody(RefreshTokenRequest(refreshToken = refreshToken))
        }.body()
    }

    override suspend fun kakaoLogin(accessToken: String): ApiResult<TokenResponse> {
        return safeApiCall {
            baseClient.post("/auth/kakao") {
                setBody(TokenRequest(accessToken = accessToken))
            }.body()
        }
    }

}
