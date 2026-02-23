package com.example.feature.resume.impl.step1

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.common.utils.getDateTimeFormatter
import com.example.core.ui.BaseViewModel
import com.example.domain.repository.ResumeDraftStoreInterface
import com.example.domain.usecase.ImageContentReadUseCase
import com.nonggle.model.ResumeWritingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ResumeStep1ViewModel @Inject constructor(
    private val resumeStore: ResumeDraftStoreInterface,
    private val imageContentReadUseCase: ImageContentReadUseCase
) : BaseViewModel<ResumeStep1Event, ResumeStep1State, ResumeStep1Effect>(initialState = ResumeStep1State()) {
    override fun onEvent(event: ResumeStep1Event) {
        when (event) {
            is ResumeStep1Event.SelectImage -> setProfileImageUri(event.imageUri)

            is ResumeStep1Event.ImageVolumeExceeded -> {
                postEffect(ResumeStep1Effect.SendToastMessage(message = event.message))
            }

            is ResumeStep1Event.UserNameChanged -> userNameChanged(event.userName)
            is ResumeStep1Event.UserNameCleared -> userNameCleared()
            is ResumeStep1Event.RemoveProfileImage -> removeProfileImageUri()

            is ResumeStep1Event.BirthDateChanged -> setBirthDate(event.birthDate)
            is ResumeStep1Event.SelectGender -> selectGender(event.gender)
            is ResumeStep1Event.ExistCertification -> existCertification(event.exist)
            is ResumeStep1Event.AddCertification -> addCertification()
            is ResumeStep1Event.CertificationChanged -> updateState { copy(certificationInput = event.certification) }
            is ResumeStep1Event.RemoveCertificationChip -> removeCertificationChip(event.id)
        }
    }

    private fun setProfileImageUri(imageUri: Uri?) {
        /// TODO: imageUri null일때 토스트 메시지 처리
        if(imageUri == null) {
            return
        }
        var imageMeta: ResumeWritingModel.ResumeImageMeta? = null
        viewModelScope.launch {
            yield()
            imageMeta = imageContentReadUseCase(imageUri.toString())

            if (imageMeta == null) {
                // TODO 토스트/에러 상태
                return@launch
            }

            updateState {
                copy(
                    info = this.info.copy(profileImageUrl = imageUri.toString())
                )
            }
            resumeStore.update { it.copy(imageMeta = imageMeta!!) }
        }
    }

    private fun removeProfileImageUri() {
        updateState {
            copy(
                info = this.info.copy(
                    profileImageUrl = null
                )
            )
        }
        resumeStore.update {
            it.copy(imageMeta = ResumeWritingModel.ResumeImageMeta())
        }
    }

    private fun userNameChanged(userName: String) {
        updateState { copy(info = this.info.copy(userName = userName)) }
        resumeStore.update {
            it.copy(userName = userName)
        }
    }

    private fun userNameCleared() {
        updateState { copy(info = this.info.copy(userName = "")) }
        resumeStore.update {
            it.copy(userName = "")
        }
    }

    private fun selectGender(gender: Gender) {
        updateState { copy(info = this.info.copy(gender = gender)) }
        resumeStore.update {
            it.copy(gender = gender.value)
        }
    }


    private fun setBirthDate(date: LocalDate) {
        updateState {
            copy(
                info = this.info.copy(birthDate = date),
                birthDate = getDateTimeFormatter(date)
            )
        }
        resumeStore.update {
            it.copy(birthDate = getDateTimeFormatter(date))
        }
    }

    private fun existCertification(exist: Boolean) {
        if (exist) {
            updateState {
                copy(certificationExist = exist)
            }
        } else {
            updateState {
                copy(
                    certificationExist = exist,
                    info = this.info.copy(certificationList = emptyList())
                )
            }
            resumeStore.update {
                it.copy(certificationList = null)
            }
        }
    }

    private fun removeCertificationChip(id: String) {
        updateState {
            val certificationList = this.info.certificationList.filter { it.id != id }
            copy(info = this.info.copy(certificationList = certificationList))
        }
        resumeStore.update {
            it.copy(certificationList = currentState.info.certificationList.map { it.certificationTitle })
        }
    }

    private fun addCertification() {
        val newCertificationList =
            currentState.info.certificationList + CertificationTag(certificationTitle = currentState.certificationInput.trim())
        updateState {
            copy(
                info = info.copy(certificationList = newCertificationList),
                certificationInput = ""
            )
        }
        resumeStore.update {
            it.copy(certificationList = newCertificationList.map {
                it.certificationTitle
            })
        }
    }

}
