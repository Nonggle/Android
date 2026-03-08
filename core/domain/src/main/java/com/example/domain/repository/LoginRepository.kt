package com.example.domain.repository

import com.nonggle.model.AppResult
import com.nonggle.model.AuthenticateToken
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun isLoggedIn(): Flow<Boolean>
    suspend fun login(accessToken: String): AppResult<AuthenticateToken>
    suspend fun logOut(): AppResult<Unit>
}
