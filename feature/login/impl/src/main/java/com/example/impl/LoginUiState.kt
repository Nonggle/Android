package com.example.impl

sealed interface LoginUiState {
    data object Idle: LoginUiState
    data object Loading: LoginUiState

    data object LoginSuccess: LoginUiState

    data object LoginFail: LoginUiState
}