package com.example.domain.usecase

import com.example.domain.repository.LoginRepository
import com.nonggle.model.AppResult
import com.nonggle.model.AuthenticateToken
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(accessToken: String): AppResult<AuthenticateToken> {
        return repository.login(accessToken)
    }
}
