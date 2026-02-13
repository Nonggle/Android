package com.nonggle.network.util

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val error: ApiError) : ApiResult<Nothing>
}

sealed interface ApiError {
    data object Network : ApiError
    data object Timeout : ApiError
    data class Http(val code: Int, val message: String? = null) : ApiError
    data object Serialization : ApiError
    data object Unauthorized : ApiError
    data object Forbidden : ApiError
    data object Unknown : ApiError
}