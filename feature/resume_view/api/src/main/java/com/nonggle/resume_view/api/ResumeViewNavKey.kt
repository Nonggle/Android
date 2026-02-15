package com.nonggle.resume_view.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class ResumeViewNavKey(
    val resumeId: String
) : NavKey