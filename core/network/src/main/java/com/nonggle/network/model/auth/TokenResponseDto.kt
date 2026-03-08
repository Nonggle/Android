package com.nonggle.network.model.auth

import com.nonggle.model.AuthenticateToken
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseDto(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)

fun TokenResponseDto.asExternalModel(): AuthenticateToken =
    AuthenticateToken(
        userId = userId,
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
