package com.nonggle.mypage.impl

import com.nonggle.domain.usecase.LogoutUseCase
import com.nonggle.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
): BaseViewModel<MyPageEvent, MyPageState, MyPageEffect>(initialState = MyPageState()) {

    override fun onEvent(event: MyPageEvent) {
        when(event) {
            else -> {}
        }
    }

}