package com.nonggle.feature.resume_view.impl

import androidx.lifecycle.viewModelScope
import com.example.core.ui.BaseViewModel
import com.example.domain.usecase.ResumeSingleViewUseCase
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEffect
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEvent
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewState
import com.nonggle.model.AppResult
import com.nonggle.model.toResumeContents
import com.nonggle.resume_view.api.ResumeViewNavKey
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ResumeViewViewModel.Factory::class)
class ResumeViewViewModel @AssistedInject constructor(
    @Assisted val navKey: ResumeViewNavKey,
    private val resumeSingleViewUseCase: ResumeSingleViewUseCase,
) : BaseViewModel<ResumeViewEvent, ResumeViewState, ResumeViewEffect>(ResumeViewState()) {

    @AssistedFactory
    interface Factory {
        fun create(navKey: ResumeViewNavKey): ResumeViewViewModel
    }

    init {
        getResumeDetail(resumeId = navKey.resumeId)

    }

    override fun onEvent(event: ResumeViewEvent) {
        when(event) {
            is ResumeViewEvent.RetryGetResumeDetail -> {
                getResumeDetail(resumeId = navKey.resumeId)
            }
        }
    }

    private fun getResumeDetail(resumeId: Long) {
        viewModelScope.launch {
            viewModelScope.launch {
                updateState { copy(isLoading = true) }
                val result = resumeSingleViewUseCase(resumeId)

                when (result) {
                    is AppResult.Success -> {
                        updateState {
                            copy(
                                isLoading = false,
                                resumeDetail = result.data.toResumeContents(),
                            )
                        }
                    }

                    is AppResult.Error -> {
                        updateState {
                            copy(
                                isLoading = false,
                                resumeRetry = true,
                            )
                        }
                    }
                }
            }
        }
    }
}