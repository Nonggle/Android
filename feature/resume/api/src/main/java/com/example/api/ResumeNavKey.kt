package com.example.feature.resume.api

import androidx.navigation3.runtime.NavKey
import com.example.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
sealed interface ResumeNavKey : NavKey {
    data object ResumeWrite: ResumeNavKey
    data object ResumeComplete: ResumeNavKey
}

fun Navigator.navigateToResume() {
    navigate(ResumeNavKey.ResumeWrite)
}