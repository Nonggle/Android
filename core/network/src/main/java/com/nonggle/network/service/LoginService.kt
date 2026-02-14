package com.nonggle.network.service

import com.nonggle.model.AppResult
import com.nonggle.network.di.AuthClient
import com.nonggle.network.model.auth.TokenRequestDto
import com.nonggle.network.model.auth.TokenResponseDto
import com.nonggle.network.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject


interface LoginService {
    suspend fun kakaoLogin(accessToken: String): AppResult<TokenResponseDto>
}

class LoginServiceImpl @Inject constructor(
    @AuthClient private val baseClient: HttpClient,
): LoginService {
    override suspend fun kakaoLogin(accessToken: String): AppResult<TokenResponseDto> {
        return safeApiCall<TokenResponseDto> {
            baseClient.post("/auth/kakao") {
                setBody(TokenRequestDto(accessToken))
            }
        }
    }
}