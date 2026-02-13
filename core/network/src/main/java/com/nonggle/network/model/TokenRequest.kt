package com.nonggle.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest (
    val refreshToken: String
)