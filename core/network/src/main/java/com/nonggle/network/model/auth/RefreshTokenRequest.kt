package com.nonggle.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest (
    val refreshToken: String
)