package com.nonggle.feature.download.impl

import androidx.lifecycle.viewModelScope
import com.example.core.ui.BaseViewModel
import com.example.domain.usecase.ResumeListViewUseCase
import com.nonggle.model.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val resumeListUseCase: ResumeListViewUseCase
) : BaseViewModel<DownloadEvent, DownloadState, DownloadEffect>(initialState = DownloadState()) {
    init {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = resumeListUseCase()
            when (result) {
                is AppResult.Success -> {
                    updateState { copy(isLoading = false) }

                }

                is AppResult.Error -> {
                }
            }
        }
    }

    override fun onEvent(event: DownloadEvent) {

    }
}