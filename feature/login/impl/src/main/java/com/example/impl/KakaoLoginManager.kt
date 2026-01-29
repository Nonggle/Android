package com.example.feature.login.impl

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoLoginManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun kakaoLogin() : Result<OAuthToken> {
        return runCatching {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(context = context)) {
                try {
                    loginWithKakaoTalk()
                } catch (e: Throwable) {
                    if (e is ClientError && e.reason == ClientErrorCause.Cancelled) {
                        /// FIXME: 사용자가 취소했다는 다이얼로그로 처리되도록 추후 수정
                        throw e
                    } else {
                        loginWithKakaoAccount()
                    }
                }
            } else {
                loginWithKakaoAccount()
            }
        }
    }

    private suspend fun loginWithKakaoTalk(): OAuthToken = suspendCoroutine { continuation ->
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            when {
                error != null -> continuation.resumeWithException(error)
                token != null -> continuation.resume(token)
                else -> continuation.resumeWithException(IllegalStateException("Login failed during kakaoTalk login"))
            }
        }
    }

    private suspend fun loginWithKakaoAccount(): OAuthToken = suspendCoroutine {  continuation ->
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            when {
                error != null -> continuation.resumeWithException(error)
                token != null -> continuation.resume(token)
                else -> continuation.resumeWithException(IllegalStateException("Login failed during kakaoAccount login"))
            }
        }
    }
}

