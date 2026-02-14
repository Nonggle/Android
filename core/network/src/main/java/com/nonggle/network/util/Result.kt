package com.nonggle.network.util

import android.util.Log
import com.nonggle.model.AppError
import com.nonggle.model.AppResult
import java.io.IOException
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> safeApiCall(
    crossinline block: suspend () -> HttpResponse
): AppResult<T> {
    return try {
        val response = block()
        val code = response.status.value

        if (code in 200..299) {
            val body: T = response.body()
            AppResult.Success(body)
        } else {
            // status 기반 매핑
            val error = when (code) {
                401 -> AppError.Unauthorized
                403 -> AppError.Forbidden
                else -> AppError.Http(code, response.status.description)
            }
            AppResult.Error(error)
        }
    } catch (e: HttpRequestTimeoutException) {
        Log.e("API_DEBUG", e.message.toString())
        AppResult.Error(AppError.Timeout)
    } catch (e: SerializationException) {
        Log.e("API_DEBUG", "SerializationException", e)
        AppResult.Error(AppError.Serialization)
    } catch (e: IOException) {
        Log.e("API_DEBUG", "IOException", e)
        AppResult.Error(AppError.Network)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        AppResult.Error(AppError.Unknown)
    }
}