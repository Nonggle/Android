package com.example.feature.login.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.feature.login.api.LoginNavKey
import com.example.feature.login.impl.LoginScreen

fun EntryProviderScope<NavKey>.LoginEntryProvider(navigateToMain: () -> Unit) {
    entry<LoginNavKey> {
        LoginScreen(
            navigateToMain = navigateToMain,
        )
    }
}