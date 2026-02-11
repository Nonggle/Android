package com.nonggle.auth

import android.util.Base64
import android.content.SharedPreferences
import com.nonggle.auth.di.TokenManager
import javax.inject.Inject
import androidx.core.content.edit
import com.google.crypto.tink.Aead

class TinkTokenManager @Inject constructor(
    private val prefs: SharedPreferences,
    private val aead: Aead
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
            // 복호화 실패(키셋 변경/손상 등)면 안전하게 토큰 무효화
            deleteToken()
            null
        }
    }

    override fun getAccessToken(): String? = decryptOrNull(KEY_ACCESS)
    override fun getRefreshToken(): String? = decryptOrNull(KEY_REFRESH)

    override fun saveTokens(accessToken: String, refreshToken: String) {
        prefs.edit {
            putString(KEY_ACCESS, encrypt(accessToken, KEY_ACCESS))
                .putString(KEY_REFRESH, encrypt(refreshToken, KEY_REFRESH))
        }
    }

    override fun deleteToken() {
        prefs.edit { remove(KEY_ACCESS).remove(KEY_REFRESH) }
    }

}