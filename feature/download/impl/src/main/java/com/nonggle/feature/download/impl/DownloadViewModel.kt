package com.nonggle.feature.download.impl

import androidx.lifecycle.viewModelScope
import com.example.core.ui.BaseViewModel
import com.example.domain.usecase.ResumeListViewUseCase
import com.nonggle.model.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Gender(val value: String) {
    MALE("남"), FEMALE("여");

    companion object {
        fun getByName(name: String): String {
            if (name == "MALE") {
                return MALE.value
            }
            return FEMALE.value
        }
    }
}

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val resumeListUseCase: ResumeListViewUseCase
) : BaseViewModel<DownloadEvent, DownloadState, DownloadEffect>(initialState = DownloadState()) {
    init {
        getResumeList()
    }

    override fun onEvent(event: DownloadEvent) {
        when (event) {
            is DownloadEvent.RetryGetResumeList -> {
                getResumeList()
            }
        }
    }

    private fun getResumeList() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = resumeListUseCase()
            when (result) {
                is AppResult.Success -> {
                    updateState {
                        copy(
                            isLoading = false,
                            resumeList = result.data,
                            errorOcuur = false
                        )
                    }
                }

                is AppResult.Error -> {
                    updateState {
                        copy(
                            isLoading = false,
                            errorOcuur = true
                        )
                    }
                }
            }
        }
    }
}