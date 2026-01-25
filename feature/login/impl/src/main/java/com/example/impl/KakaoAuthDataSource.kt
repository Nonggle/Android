package com.example.impl

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoAuthDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun UserApiClient.Companion.login(context: Context): Result<OAuthToken> = runCatching {
        if (instance.isKakaoTalkLoginAvailable(context)) {
            try {
                UserApiClient.loginWithKakaoTalk(context)
            } catch (error: Throwable) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    throw error
                } else {
                    UserApiClient.loginWithKakaoAccount(context)
                }
            }
        } else {
            UserApiClient.loginWithKakaoAccount(context)
        }
    }

    // 카카오톡으로 로그인 시도
    private suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken =
        suspendCoroutine { continuation ->
            instance.loginWithKakaoTalk(context) { token, error ->
                continuation.resumeTokenOrException(token, error)
            }
        }

    // 카카오 계정으로 로그인 시도
    private suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): OAuthToken =
        suspendCoroutine { continuation ->
            instance.loginWithKakaoAccount(context) { token, error ->
                continuation.resumeTokenOrException(token, error)
            }
        }

    private fun Continuation<OAuthToken>.resumeTokenOrException(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            resumeWithException(error)
        } else if (token != null) {
            resume(token)
        } else {
            resumeWithException(RuntimeException("Can't Receive Kakao Access Token"))
        }
    }
}
