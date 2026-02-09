package com.example.feature.resume.api

import androidx.navigation3.runtime.NavKey
import com.example.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
object ResumeNavKey : NavKey

fun Navigator.navigateToResume() {
    navigate(ResumeNavKey)
}