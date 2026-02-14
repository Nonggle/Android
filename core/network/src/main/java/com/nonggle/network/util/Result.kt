package com.nonggle.network.util

import com.nonggle.model.AppError
import com.nonggle.model.AppResult
import com.nonggle.network.model.ApiResponse
import io.ktor.client.call.body
import android.util.Log
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.SerializationException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> safeApiCall(
    crossinline block: suspend () -> HttpResponse
): AppResult<T> {
    return try {
        val response = block()
        val httpCode = response.status.value

        if(response.status.isSuccess()) {
            val envelope: ApiResponse<T> = response.body()
            if(envelope.success) {
                val data = envelope.data
                    ?: return AppResult.Error(AppError.Serialization) // success인데 data 없음
                AppResult.Success(data)
            } else {
                val errorMsg = envelope.error?.message
                AppResult.Error(AppError.Http(httpCode, errorMsg))
            }
        } else {
            // 2) 서버에서 error가 내려오면 그걸 우선 반영
            val envelope = runCatching { response.body<ApiResponse<T>>() }.getOrNull()
            val serverError = envelope?.error

            if (serverError != null) {
                // 서버 error.code를 그대로 쓰거나, httpCode와 함께 묶어서 쓰는 방식 중 택1
                val mapped = when (serverError.code) {
                    401 -> AppError.Unauthorized
                    403 -> AppError.Forbidden
                    else -> AppError.Http(serverError.code, serverError.message)
                }
                AppResult.Error(mapped)
            } else {
                // 3) error가 없으면 HTTP status 기반 fallback
                val mapped = when (httpCode) {
                    401 -> AppError.Unauthorized
                    403 -> AppError.Forbidden
                    else -> AppError.Http(httpCode, response.status.description)
                }
                AppResult.Error(mapped)
            }
        }
        ///FIXME: 로그 삭제 예정
    } catch (e: HttpRequestTimeoutException) {
        Log.e("API_DEBUG", "Timeout", e)
        AppResult.Error(AppError.Timeout)
    } catch (e: JsonConvertException) { // Ktor가 감싸서 던지는 경우가 많음
        Log.e("API_DEBUG", "JsonConvertException", e)
        AppResult.Error(AppError.Serialization)
    } catch (e: SerializationException) {
        Log.e("API_DEBUG", "SerializationException", e)
        AppResult.Error(AppError.Serialization)
    } catch (e: IOException) {
        Log.e("API_DEBUG", "IOException", e)
        AppResult.Error(AppError.Network)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Log.e("API_DEBUG", "Unknown", e)
        AppResult.Error(AppError.Unknown)
    }
}
