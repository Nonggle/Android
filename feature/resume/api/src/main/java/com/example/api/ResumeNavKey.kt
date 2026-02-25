package com.example.feature.resume.api

import androidx.navigation3.runtime.NavKey
import com.example.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
sealed interface ResumeNavKey : NavKey {
    @Serializable
    data object ResumeWrite: ResumeNavKey
    @Serializable
    data object ResumeComplete: ResumeNavKey
}

fun Navigator.navigateToResume() {
    navigate(ResumeNavKey.ResumeWrite)
}