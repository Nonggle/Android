package com.nonggle.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequestDto(
    val accessToken: String
)
