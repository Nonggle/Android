package com.example.feature.resume.impl

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState


data class ResumeState(
    val isLoading: Boolean = true,
    val profileImageUrl: String = "",
) : UiState

sealed interface ResumeEvent: UiEvent {

}

sealed interface ResumeEffect: UiEffect {

}