package com.example.feature.resume.impl.step2

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState

data class Career(
    val companyName: String? = null,
    val role: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val description: String? = null
)

data class ResumeStep2State(
    val isLoading: Boolean = true,
    val career: Career = Career()
): UiState

sealed interface ResumeStep2Event : UiEvent {

}

sealed interface ResumeStep2Effect : UiEffect {

}