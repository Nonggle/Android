package com.nonggle.resume_view.api

import androidx.navigation3.runtime.NavKey
import com.example.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class ResumeViewNavKey(val resumeId: Long) : NavKey

fun Navigator.navigateToResumeView(resumeId: Long) {
    navigate(ResumeViewNavKey(resumeId = resumeId))
}