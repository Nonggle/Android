package com.nonggle.nonggleresume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nonggle.common.result.AuthEvent
import com.nonggle.common.result.AuthEventBus
import com.nonggle.domain.repository.LoginRepository
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

        viewModelScope.launch {
            authEventBus.events.collect { event ->
                when (event) {
                    AuthEvent.SessionExpired -> {
                        loginRepository.logOut()
                        _isLoggedIn.value = false
                    }

                    AuthEvent.LoggedOut -> {
                        _isLoggedIn.value = false
                    }
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

    // 다크 테마 사용 필요성
    fun shouldUseDarkTheme(isSystemDarkTheme: Boolean) = isSystemDarkTheme
}
