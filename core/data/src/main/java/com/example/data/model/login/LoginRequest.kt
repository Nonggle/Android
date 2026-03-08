package com.example.data.model.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    private val accessToken: String,
)
