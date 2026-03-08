package com.example.data.model.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)
