package com.example.impl.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.LoginNavKey
import com.example.impl.LoginScreen

fun EntryProviderScope<NavKey>.LoginEntry(
    onLoginSuccess: () -> Unit,
) {
    entry<LoginNavKey> {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
        )
    }
}