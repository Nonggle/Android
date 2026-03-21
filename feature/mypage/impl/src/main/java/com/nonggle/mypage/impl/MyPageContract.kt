package com.nonggle.mypage.impl

import com.nonggle.ui.UiEffect
import com.nonggle.ui.UiEvent
import com.nonggle.ui.UiState

data class MyPageState(
    val isLoading: Boolean = false
): UiState

sealed interface MyPageEvent: UiEvent {
    data object LogoutClicked : MyPageEvent
}

sealed interface MyPageEffect: UiEffect {
    data object LogoutFailed : MyPageEffect
}
