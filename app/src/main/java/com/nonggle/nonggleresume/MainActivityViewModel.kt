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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    authEventBus: AuthEventBus,
    private val loginRepository: LoginRepository,
) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _uiState = MutableStateFlow<MainActivityUiState>(MainActivityUiState.Loading)
    val uiState: StateFlow<MainActivityUiState> = _uiState

    init {
        refreshLoginState()

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
    }

    fun onLoginSuccess() {
        _isLoggedIn.value = true
    }

    fun onResume() {
        refreshLoginState()
    }

    private fun refreshLoginState() {
        viewModelScope.launch {
            _uiState.value = MainActivityUiState.Loading
            _isLoggedIn.value = loginRepository.isLoggedIn().first()
            _uiState.value = MainActivityUiState.Success
        }
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
