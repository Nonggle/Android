package com.example.feature.login.impl

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState

sealed interface LoginUiState {
    data object Idle: LoginUiState // 로그인 이전
    data object Loading: LoginUiState

    data object LoginSuccess: LoginUiState

    data object LoginFail: LoginUiState
}

data class LoginState (
    val isLoading: Boolean = false,
    val loginState: LoginUiState = LoginUiState.Idle
): UiState

sealed interface LoginEvent: UiEvent {
    data object KakaoLoginButtonClick: LoginEvent
}

sealed interface LoginEffect: UiEffect {
    data object navigateToHome: LoginEffect
}
