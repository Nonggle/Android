package com.nonggle.mypage.impl

import androidx.lifecycle.viewModelScope
import com.nonggle.model.AppResult
import com.nonggle.domain.usecase.LogoutUseCase
import com.nonggle.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
): BaseViewModel<MyPageEvent, MyPageState, MyPageEffect>(initialState = MyPageState()) {

    override fun onEvent(event: MyPageEvent) {
        when(event) {
            MyPageEvent.LogoutClicked -> logout()
        }
    }

    private fun logout() {
        if (currentState.isLoading) return

        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (logoutUseCase()) {
                is AppResult.Success -> {
                    updateState { copy(isLoading = false) }
                }

                is AppResult.Error -> {
                    updateState { copy(isLoading = false) }
                }
            }
        }
    }
}
