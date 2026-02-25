package com.nonggle.network.util

import com.nonggle.model.AppError
import com.nonggle.model.AppResult
import com.nonggle.network.model.ApiResponse
import io.ktor.client.call.body
import android.util.Log
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
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

        if (response.status.isSuccess()) {
            val envelope: ApiResponse<T> = response.body()
            if (envelope.success) {
                val data = envelope.data
                    ?: return AppResult.Error(AppError.Serialization) // success인데 data 없음
                AppResult.Success(data)
            } else {
                val errorMsg = envelope.error?.message
                AppResult.Error(AppError.Http(httpCode, errorMsg))
            }
        } else {
            AppResult.Error(AppError.Http(response.status.value, response.bodyAsText() ?: "Unhandled HTTP error"))
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
