package com.nonggle.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse (
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)