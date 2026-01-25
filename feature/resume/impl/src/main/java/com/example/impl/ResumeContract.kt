package com.example.impl


data class ResumeState(
    val isLoading: Boolean = true,
    val profileImageUrl: String = "",
) : UiState

sealed interface ResumeEvent: UiEvent

sealed interface ResumeEffect: UiEffect