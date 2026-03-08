package com.nonggle.auth.di

interface TokenManager {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?

    suspend fun saveTokens(accessToken: String, refreshToken: String)

    suspend fun deleteToken()

    suspend fun hasAccessToken(): Boolean = !getAccessToken().isNullOrEmpty()
    suspend fun hasRefreshToken(): Boolean = !getRefreshToken().isNullOrEmpty()
}
