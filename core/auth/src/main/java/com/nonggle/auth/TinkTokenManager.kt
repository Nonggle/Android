package com.nonggle.auth

import android.content.SharedPreferences
import android.util.Base64
import androidx.core.content.edit
import com.google.crypto.tink.Aead
import com.nonggle.auth.di.TokenManager
import com.nonggle.common.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

// / 토큰 저장소 + 암호화
class TinkTokenManager @Inject constructor(
    private val prefs: SharedPreferences,
    private val aead: Aead,
    @IoDispatcher private val io: CoroutineDispatcher
) : TokenManager {
    private companion object {
        const val KEY_ACCESS = "access_token"
        const val KEY_REFRESH = "refresh_token"
    }

    private fun encrypt(plain: String, associatedKey: String): String {
        val cipherBytes = aead.encrypt(
            plain.toByteArray(Charsets.UTF_8),
            associatedKey.toByteArray(Charsets.UTF_8) // associated data
        )
        return Base64.encodeToString(cipherBytes, Base64.NO_WRAP)
    }

    private fun decryptOrNull(key: String): String? {
        val b64 = prefs.getString(key, null) ?: return null
        return try {
            val cipherBytes = Base64.decode(b64, Base64.NO_WRAP)
            val plainBytes = aead.decrypt(
                cipherBytes,
                key.toByteArray(Charsets.UTF_8) // 저장 때와 동일해야 함
            )
            String(plainBytes, Charsets.UTF_8)
        } catch (_: Throwable) {
            null
        }
    }

    override suspend fun getAccessToken(): String? = withContext(io) {
        val token = decryptOrNull(KEY_ACCESS)
        if (token == null && prefs.contains(KEY_ACCESS)) {
            // "저장돼있는데 복호화 실패" 케이스 → 안전하게 무효화
            deleteToken()
        }
        token
    }

    override suspend fun getRefreshToken(): String? = withContext(io) {
        val token = decryptOrNull(KEY_REFRESH)
        if (token == null && prefs.contains(KEY_REFRESH)) {
            deleteToken()
        }
        token
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) = withContext(io) {
        prefs.edit {
            putString(KEY_ACCESS, encrypt(accessToken, KEY_ACCESS))
            putString(KEY_REFRESH, encrypt(refreshToken, KEY_REFRESH))
        }
    }

    override suspend fun deleteToken() = withContext(io) {
        prefs.edit {
            remove(KEY_ACCESS)
            remove(KEY_REFRESH)
        }
    }
}
