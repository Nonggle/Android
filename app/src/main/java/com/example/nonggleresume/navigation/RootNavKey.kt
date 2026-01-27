package com.example.nonggleresume.navigation
import androidx.navigation3.runtime.NavKey

sealed interface RootNavKey: NavKey {
    data object LoginNavKey: RootNavKey
    data object MainNavKey: RootNavKey
}
