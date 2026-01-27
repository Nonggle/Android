package com.example.api

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.impl.LoginScreen

fun EntryProviderScope<NavKey>.LoginEntryProvider(
    onLoginSuccess: () -> Unit,
) {
    entry<LoginNavKey> {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
        )
    }
}