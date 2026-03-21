package com.nonggle.mypage.impl

import com.nonggle.ui.UiEffect
import com.nonggle.ui.UiEvent
import com.nonggle.ui.UiState

data class MyPageState(
    val isLoading: Boolean = false
): UiState

sealed interface MyPageEvent: UiEvent {

}

sealed interface MyPageEffect: UiEffect {

}