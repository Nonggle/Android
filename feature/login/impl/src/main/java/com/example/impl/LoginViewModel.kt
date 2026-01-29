package com.example.feature.login.impl

import androidx.lifecycle.viewModelScope
import com.example.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginManager: KakaoLoginManager
) : BaseViewModel<LoginEvent, LoginState, LoginEffect>(
    initialState = LoginState()
) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.KakaoLoginButtonClick -> kakaoLoginButtonClick()
        }
    }

    private fun kakaoLoginButtonClick() {
        viewModelScope.launch {
            updateState {copy(isLoading = true)}
            kakaoLoginManager.kakaoLogin()
                .onSuccess {
                    updateState {
                        copy(
                            loginState = LoginUiState.LoginSuccess,
                        )
                    }
                }
                .onFailure {
                    updateState {
                        copy(loginState = LoginUiState.LoginFail)
                    }
                }
            updateState {copy(isLoading = false)}
        }
    }
}
