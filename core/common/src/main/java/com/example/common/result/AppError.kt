package com.example.common.result

sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Error(val error: AppError) : AppResult<Nothing>
}

sealed interface AppError {
    data object Network : AppError
    data object Timeout : AppError
    data class Http(val code: Int, val message: String? = null) : AppError
    data object Serialization : AppError
    data object Unauthorized : AppError
    data object Forbidden : AppError
    data object Unknown : AppError
}