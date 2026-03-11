package com.nonggle.login.impl

import androidx.lifecycle.viewModelScope
import com.nonggle.ui.BaseViewModel
import com.nonggle.domain.usecase.KakaoLoginUseCase
import com.nonggle.impl.KakaoLoginManager
import com.nonggle.impl.LoginEffect
import com.nonggle.impl.LoginEvent
import com.nonggle.impl.LoginState
import com.nonggle.impl.LoginUiState
import com.nonggle.model.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginManager: KakaoLoginManager,
    private val loginUseCase: KakaoLoginUseCase
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
                    getToken(it.accessToken)
                }
                .onFailure {
                    updateState {
                        copy(loginState = LoginUiState.LoginFail)
                    }
                }
            updateState {copy(isLoading = false)}
        }
    }

    private fun getToken(accessToken: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            when (val result = loginUseCase(accessToken)) {
                is AppResult.Success -> {
                    updateState { copy(isLoading = false, loginState = LoginUiState.LoginSuccess) }
                }

                is AppResult.Error -> {
                    // 여기서 AppError 타입별 분기 가능
                    updateState { copy(isLoading = false, loginState = LoginUiState.LoginFail) }
                }
            }
        }
    }
}
