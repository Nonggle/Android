package com.example.feature.home.impl

import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): BaseViewModel<HomeEvent, HomeState, HomeEffect>(initialState = HomeState()) {
    override fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.NavigateToResumeWritingScreen -> {
                postEffect(HomeEffect.NavigateToResumeWritingScreen)
            }
        }
    }

}