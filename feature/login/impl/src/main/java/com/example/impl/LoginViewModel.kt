package com.example.feature.login.impl

import androidx.lifecycle.viewModelScope
import com.example.core.ui.BaseViewModel
import com.example.domain.repository.LoginRepository
import com.example.domain.usecase.KakaoLoginUseCase
import com.nonggle.model.AppResult
import com.nonggle.model.AuthenticateToken
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
