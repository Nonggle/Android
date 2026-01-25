package com.example.impl
import com.example.impl.KakaoAuthDataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoAuthDataSource: KakaoAuthDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun kakaoLoginButtonClick() {
        viewModelScope.launch {
//            _uiState.value = LoginUiState.Loading
//            runCatching {
//                kakaoAuthDataSource.login()
//            }.onSuccess { token ->
//                Log.i("LoginViewModel", "로그인 성공 ${token.accessToken}")
//                _uiState.value = LoginUiState.LoginSuccess
//            }.onFailure { throwable ->
//                if (throwable is java.util.concurrent.CancellationException) {
//                    throw throwable
//                }
//                Log.e("LoginViewModel", "로그인 실패", throwable)
//                _uiState.value = LoginUiState.LoginFail
//            }
           // UserApiClient.login()
        }
    }



}
