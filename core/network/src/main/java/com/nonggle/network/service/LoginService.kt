package com.nonggle.network.service

import com.nonggle.common.network.IoDispatcher
import com.nonggle.model.AppResult
import com.nonggle.network.di.ApiClient
import com.nonggle.network.model.auth.TokenRequestDto
import com.nonggle.network.model.auth.TokenResponseDto
import com.nonggle.network.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


interface LoginService {
    suspend fun kakaoLogin(accessToken: String): AppResult<TokenResponseDto?>

    suspend fun logout(): AppResult<Unit?>
}

class LoginServiceImpl @Inject constructor(
    @ApiClient private val baseClient: HttpClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): LoginService {
    override suspend fun kakaoLogin(accessToken: String): AppResult<TokenResponseDto?> {
        return safeApiCall(ioDispatcher) {
            baseClient.post("/auth/kakao") {
                setBody(TokenRequestDto(accessToken))
            }
        }
    }

    override suspend fun logout(): AppResult<Unit?> {
        return safeApiCall(ioDispatcher) {
            baseClient.post("/logout")
        }
    }
}