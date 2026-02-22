package com.example.feature.home.impl

import androidx.lifecycle.viewModelScope
import com.example.core.ui.BaseViewModel
import com.example.domain.usecase.ResumeListViewUseCase
import com.nonggle.model.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resumeListUseCase: ResumeListViewUseCase
): BaseViewModel<HomeEvent, HomeState, HomeEffect>(initialState = HomeState()) {
    init {
        viewModelScope.launch {
            val result = resumeListUseCase()
            when(result) {
                is AppResult.Success -> {

                }
                is AppResult.Error -> {

                }
            }
        }
    }

    override fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.NavigateToResumeWritingScreen -> {
                postEffect(HomeEffect.NavigateToResumeWritingScreen)
            }
        }
    }

}