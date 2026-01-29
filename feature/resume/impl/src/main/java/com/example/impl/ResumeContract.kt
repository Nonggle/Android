package com.example.feature.resume.impl

import com.example.ui.UiEffect
import com.example.ui.UiEvent
import com.example.ui.UiState


data class ResumeState(
    val isLoading: Boolean = true,
    val profileImageUrl: String = "",
) : UiState

sealed interface ResumeEvent: UiEvent {

}

sealed interface ResumeEffect: UiEffect {

}