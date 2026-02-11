package com.nonggle.auth.di

import android.content.Context
import android.content.SharedPreferences
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.nonggle.auth.TinkTokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    private const val PREF_FILE = "nonggle_auth_prefs"          // 토큰 저장 prefs
    private const val KEYSET_PREF_FILE = "nonggle_tink_keyset"  // 키셋 저장 prefs
    private const val KEYSET_NAME = "aead_keyset"
    private const val MASTER_KEY_URI = "android-keystore://nonggle_tink_master_key"


    @Provides
    @Singleton
    fun provideAuthPrefs(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAead(
        @ApplicationContext context: Context
    ): Aead {
        // 1) Tink primitive 등록(1회)
        AeadConfig.register()

        // 2) 키셋 생성/로드 + Android Keystore로 보호
        val handle = AndroidKeysetManager.Builder()
            .withSharedPref(context, KEYSET_NAME, KEYSET_PREF_FILE)
            .withKeyTemplate(KeyTemplates.get("AES256_GCM")) // 일반적으로 추천되는 AEAD 템플릿
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle

        return handle.getPrimitive(RegistryConfiguration.get(),Aead::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenManager(
        prefs: SharedPreferences,
        aead: Aead
    ): TokenManager = TinkTokenManager(prefs, aead)
}