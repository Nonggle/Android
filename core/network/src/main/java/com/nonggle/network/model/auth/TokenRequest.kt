package com.nonggle.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val accessToken: String
)
