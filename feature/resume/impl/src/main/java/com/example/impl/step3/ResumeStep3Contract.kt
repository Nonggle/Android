package com.example.feature.resume.impl.step3

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState

data class Portfolio(
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val skills: List<String> = emptyList()
)

data class ResumeStep3State(
    val isLoading: Boolean = true,
    val portfolio: Portfolio = Portfolio()
): UiState

sealed interface ResumeStep3Event : UiEvent {

}

sealed interface ResumeStep3Effect : UiEffect {

}