package com.nonggle.feature.resume_view.impl

import androidx.lifecycle.viewModelScope
import com.example.ui.BaseViewModel
import com.example.domain.usecase.ResumeSingleViewUseCase
import com.nonggle.feature.resume_view.impl.navigation.ResumeReadEffect
import com.nonggle.feature.resume_view.impl.navigation.ResumeReadEvent
import com.nonggle.feature.resume_view.impl.navigation.ResumeReadState
import com.nonggle.feature.resume_view.impl.navigation.ScreenMode
import com.nonggle.model.AppResult
import com.nonggle.model.toResumeContents
import com.nonggle.resume_view.api.ResumeViewNavKey
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ResumeReadViewModel.Factory::class)
class ResumeReadViewModel @AssistedInject constructor(
    @Assisted val navKey: ResumeViewNavKey,
    private val resumeSingleViewUseCase: ResumeSingleViewUseCase,
) : BaseViewModel<ResumeReadEvent, ResumeReadState, ResumeReadEffect>(ResumeReadState()) {

    @AssistedFactory
    interface Factory {
        fun create(navKey: ResumeViewNavKey): ResumeReadViewModel
    }

    init {
        getResumeDetail(resumeId = navKey.resumeId)

    }

    override fun onEvent(event: ResumeReadEvent) {
        when (event) {
            is ResumeReadEvent.RetryGetResumeDetail -> {
                getResumeDetail(resumeId = navKey.resumeId)
            }

            is ResumeReadEvent.ClickBackIconButton -> {
                postEffect(ResumeReadEffect.NavigateToBack)
            }

            is ResumeReadEvent.DownloadResumeToLocal -> {
                updateState { copy(screenMode = ScreenMode.PDF) }
                postEffect(ResumeReadEffect.DownLoadPDF)
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
        postEffect(ResumeReadEffect.DownLoadSuccess)
    }

    fun generateDownloadFailToastMessage(message: String) {
        postEffect(ResumeReadEffect.DownLoadFailure(message))
    }
}