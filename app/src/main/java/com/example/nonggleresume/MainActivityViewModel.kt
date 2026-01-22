package com.example.nonggleresume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import com.example.nonggleresume.MainActivityUiState.Loading
import com.example.nonggleresume.MainActivityUiState.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<MainActivityUiState>(Loading)
    val uiState: StateFlow<MainActivityUiState> = _uiState

    init {
        viewModelScope.launch {
            // TODO: 앱에서 초기화 작업 추후 선언하기
            _uiState.value = Success
        }
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