package com.nonggle.feature.resume_view.impl

import androidx.lifecycle.viewModelScope
import com.example.core.ui.BaseViewModel
import com.example.domain.usecase.ResumeSingleViewUseCase
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEffect
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEvent
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewState
import com.nonggle.feature.resume_view.impl.navigation.ScreenMode
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
        when (event) {
            is ResumeViewEvent.RetryGetResumeDetail -> {
                getResumeDetail(resumeId = navKey.resumeId)
            }

            is ResumeViewEvent.ClickBackIconButton -> {
                postEffect(ResumeViewEffect.NavigateToBack)
            }

            is ResumeViewEvent.DownloadResumeToLocal -> {
                updateState { copy(screenMode = ScreenMode.PDF) }
                postEffect(ResumeViewEffect.DownLoadPDF)
            }
        }
    }

    private fun getResumeDetail(resumeId: Long) {
        viewModelScope.launch {
            updateState { copy(isLoading = true, resumeRetry = false) }
            val result = resumeSingleViewUseCase(resumeId)

            when (result) {
                is AppResult.Success -> {
                    updateState {
                        copy(
                            isLoading = false,
                            resumeRetry = false,
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

    fun updatePermissionGranted(isGranted: Boolean) {
        updateState { copy(writeExternalPermissionGranted = isGranted) }
    }

    fun updateScreenMode(screenMode: ScreenMode) {
        updateState { copy(screenMode = screenMode) }
    }

    fun generateDownloadSuccessToastMessage() {
        postEffect(ResumeViewEffect.DownLoadSuccess)
    }

    fun generateDownloadFailToastMessage(message: String) {
        postEffect(ResumeViewEffect.DownLoadFailure(message))
    }
}