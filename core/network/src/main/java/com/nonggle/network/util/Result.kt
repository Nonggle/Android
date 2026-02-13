package com.nonggle.network.util

import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> safeApiCall(
    crossinline block: suspend () -> HttpResponse
): ApiResult<T> {
    return try {
        val response = block()
        val code = response.status.value

        if (code in 200..299) {
            val body: T = response.body()
            ApiResult.Success(body)
        } else {
            // status 기반 매핑
            val error = when (code) {
                401 -> ApiError.Unauthorized
                403 -> ApiError.Forbidden
                else -> ApiError.Http(code, response.status.description)
            }
            ApiResult.Error(error)
        }
    } catch (e: HttpRequestTimeoutException) {
        ApiResult.Error(ApiError.Timeout)
    } catch (e: SerializationException) {
        ApiResult.Error(ApiError.Serialization)
    } catch (e: IOException) {
        ApiResult.Error(ApiError.Network)
    } catch (e: Exception) {
        ApiResult.Error(ApiError.Unknown)
    }
}