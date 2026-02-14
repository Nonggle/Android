package com.nonggle.model

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

inline fun <T, R> AppResult<T>.map(transform: (T) -> R): AppResult<R> = when (this) {
    is AppResult.Success -> AppResult.Success(transform(data))
    is AppResult.Error -> this
}

inline fun <T> AppResult<T>.getOrNull(): T? = when (this) {
    is AppResult.Success -> data
    is AppResult.Error -> null
}
