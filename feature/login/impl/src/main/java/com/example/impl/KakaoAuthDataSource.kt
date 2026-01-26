package com.example.impl

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoAuthDataSource @Inject constructor(@ApplicationContext private val context: Context /// FIXME: Application Context를 삽입해도 괜찮은가?
) {

    suspend fun kakaoLogin() : Result<OAuthToken> {
        return runCatching {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(context = context)) {
                try {
                    loginWithKakaoTalk(context)
                } catch (e: Throwable) {
                    if (e is ClientError && e.reason == ClientErrorCause.Cancelled) {
                        throw e
                    } else {
                        loginWithKakaoAccount(context)
                    }
                }
            } else {
                loginWithKakaoAccount(context)
            }
        }
    }

    // 카카오톡으로 로그인 시도
    private suspend fun loginWithKakaoTalk(context: Context): OAuthToken {
        return suspendCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                continuation.resumeTokenOrException(token, error)
            }
        }
    }

    // 카카오 계정으로 로그인 시도
    private suspend fun loginWithKakaoAccount(context: Context): OAuthToken =
        suspendCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
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
