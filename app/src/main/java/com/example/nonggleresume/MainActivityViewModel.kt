package com.example.nonggleresume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.result.AuthEvent
import com.example.common.result.AuthEventBus
import com.example.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    authEventBus: AuthEventBus,
    private val loginRepository: LoginRepository,
    // TODO: Add a UseCase to check the initial login status
) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _uiState = MutableStateFlow<MainActivityUiState>(MainActivityUiState.Loading)
    val uiState: StateFlow<MainActivityUiState> = _uiState

    init {
        viewModelScope.launch {
            loginRepository.isLoggedIn().collect { isLoggedIn ->
                _isLoggedIn.value = isLoggedIn
            }
        }
        // Listen for session expiration events
        viewModelScope.launch {
            /// TODO: 로그인 상태 확인 -> 토큰이 정상적으로 존재하는지 확인
            // _isLoggedIn.value = authRepository.isLoggedIn()
            authEventBus.events.collect { event ->
                if (event is AuthEvent.SessionExpired) {
                    loginRepository.logOut()  // 토큰 삭제
                    _isLoggedIn.value = false  // 이것만으로 로그인 화면 전환 + backstack 자동 소멸
                }
            }
        }

        _uiState.value = MainActivityUiState.Success
    }

    fun onLoginSuccess() {
        _isLoggedIn.value = true
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState

    data object Success : MainActivityUiState {
        override fun shouldUseDarkTheme(isSystemDarkTheme: Boolean) = isSystemDarkTheme
    }

    // 스플래시 화면 상태를 유지해야하는지에 대한 여부
    fun shouldKeepSplashScreen() = this is Loading

    // 다크 테마 사용 필요성
    fun shouldUseDarkTheme(isSystemDarkTheme: Boolean) = isSystemDarkTheme
}