package com.example.data.repositoryimpl

import com.example.domain.repository.LoginRepository
import com.nonggle.auth.di.TokenManager
import com.nonggle.model.AppResult
import com.nonggle.model.AuthenticateToken
import com.nonggle.model.map
import com.nonggle.network.model.auth.TokenResponseDto
import com.nonggle.network.model.auth.asExternalModel
import com.nonggle.network.service.LoginService
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService,
    private val tokenManager: TokenManager
) : LoginRepository {

    override suspend fun kakaoLogin(accessToken: String): AppResult<AuthenticateToken> {
        // AuthService를 통해 API를 호출합니다.
        val apiResult = loginService.kakaoLogin(accessToken)

        // API 호출 결과가 성공일 때만 토큰을 저장합니다.
        if (apiResult is AppResult.Success) {
            val tokenResponse = apiResult.data
            // Repository가 TokenManager를 사용하여 토큰을 저장합니다.
            tokenManager.saveTokens(
                accessToken = tokenResponse.accessToken,
                refreshToken = tokenResponse.refreshToken
            )
        }

        // UseCase에는 API 호출 결과만 반환합니다.
        return apiResult.map(TokenResponseDto::asExternalModel)
    }
}