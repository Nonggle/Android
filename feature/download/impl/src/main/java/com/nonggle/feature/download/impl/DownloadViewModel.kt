package com.nonggle.feature.download.impl

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.example.core.ui.BaseViewModel
import com.example.domain.usecase.ResumeDeleteUseCase
import com.example.domain.usecase.ResumeListViewUseCase
import com.nonggle.model.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.jar.Manifest
import javax.inject.Inject
@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val resumeListUseCase: ResumeListViewUseCase,
    private val resumeDeleteUseCase: ResumeDeleteUseCase,
) : BaseViewModel<DownloadEvent, DownloadState, DownloadEffect>(initialState = DownloadState()) {

    init {
        getResumeList()
    }

    override fun onEvent(event: DownloadEvent) {
        when (event) {
            is DownloadEvent.RetryGetResumeList -> {
                getResumeList()
            }

            is DownloadEvent.deleteResumeItem -> {
                deleteResumeItem(event.resumeId)
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
                            resumeList = result.data ?: currentState.resumeList,
                            isError = false
                        )
                    }
                }

                is AppResult.Error -> {
                    updateState {
                        copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    private fun deleteResumeItem(resumeId: Long) {
        val deleteResumeItem = currentState.resumeList.find { it.id == resumeId } ?: return
        viewModelScope.launch {
            updateState {
                copy(resumeList = resumeList.filter { it.id != resumeId })
            }

            val result = resumeDeleteUseCase(resumeId)
            when(result) {
                is AppResult.Error -> {
                    // 삭제 실패했으므로 ui 복구
                    updateState { copy(
                        resumeList = resumeList + deleteResumeItem
                    ) }
                    postEffect(effect = DownloadEffect.ShowErrorToastMessage)
                }
                else -> {}
            }
        }
    }

}