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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    crossinline block: suspend () -> HttpResponse
): AppResult<T?> = withContext(dispatcher) {
    try {
        val response = block()
        val httpCode = response.status.value

        if (response.status.isSuccess()) {
            val envelope: ApiResponse<T> = response.body()
            if (envelope.success) {
                AppResult.Success(envelope.data)
            } else {
                AppResult.Error(AppError.Http(httpCode, envelope.error?.message))
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
