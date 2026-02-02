package com.example.nonggleresume.navigation
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface RootNavKey : NavKey {
    @Serializable
    data object LoginNavKey : RootNavKey
    @Serializable
    data object MainNavKey : RootNavKey
}
