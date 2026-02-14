package com.nonggle.model

data class AuthenticateToken(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
)