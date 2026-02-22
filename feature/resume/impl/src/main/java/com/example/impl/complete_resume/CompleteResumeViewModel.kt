package com.example.impl.complete_resume

import androidx.lifecycle.viewModelScope
import com.example.core.ui.BaseViewModel
import com.example.domain.usecase.ResumeCreateUseCase
import com.nonggle.model.AppError
import com.nonggle.model.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompleteResumeViewModel @Inject constructor(
    private val resumeCreateUseCase: ResumeCreateUseCase,
): BaseViewModel<CompleteResumeEvent, CompleteResumeState, CompleteResumeEffect>(
    CompleteResumeState()
) {
    init {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val appResult = resumeCreateUseCase()
            when(appResult) {
                is AppResult.Success -> {
                    updateState { copy(
                        isLoading = false,
                        uploadSuccess = true,
                        id = appResult.data.id
                    ) }
                }
                is AppResult.Error -> {
                    if(appResult.error is AppError.Unknown) {
                        postEffect(effect = CompleteResumeEffect.ShowErrorMessage("관리자에게 문의 바랍니다."))
                    }
                    updateState { copy(
                        isLoading = false,
                        uploadSuccess = false
                    ) }
                }
            }
        }
    }

    override fun onEvent(event: CompleteResumeEvent) {
    }
}