package com.example.domain.repository

import com.nonggle.model.AppResult
import com.nonggle.model.AuthenticateToken

interface LoginRepository {
    suspend fun kakaoLogin(accessToken: String): AppResult<AuthenticateToken>
}