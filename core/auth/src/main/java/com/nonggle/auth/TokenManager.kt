package com.nonggle.auth.di

interface TokenManager {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?

    fun saveTokens(accessToken: String, refreshToken: String)

    fun deleteToken()

    fun hasAccessToken(): Boolean = !getAccessToken().isNullOrEmpty()
    fun hasRefreshToken(): Boolean = !getRefreshToken().isNullOrEmpty()
}