package com.nonggle.network.util

import com.nonggle.network.BuildConfig

object NetworkConfig {
    val baseUrl: String get() = BuildConfig.BASE_URL
    const val TIMEOUT_MS = 5_000L
    const val APP_VERSION = BuildConfig.VERSION_NAME
}