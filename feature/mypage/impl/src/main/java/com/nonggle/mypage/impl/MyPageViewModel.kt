package com.nonggle.mypage.impl

import androidx.lifecycle.viewModelScope
import com.nonggle.common.result.AuthEvent
import com.nonggle.common.result.AuthEventBus
import com.nonggle.model.AppResult
import com.nonggle.domain.usecase.LogoutUseCase
import com.nonggle.impl.KakaoLoginManager
import com.nonggle.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val kakaoLoginManager: KakaoLoginManager,
    private val authEventBus: AuthEventBus,
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
            val apiResult = logoutUseCase()
            val kakaoResult = kakaoLoginManager.kakaoLogout()

            if (apiResult is AppResult.Error || kakaoResult.isFailure) {
                postEffect(MyPageEffect.LogoutFailed)
            }

            authEventBus.emit(AuthEvent.LoggedOut)
            updateState { copy(isLoading = false) }
        }
    }
}
