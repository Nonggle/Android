package com.nonggle.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorDto? = null
) {
    @Serializable
    data class ErrorDto (
        val code: Int,
        val message: String
    )
}