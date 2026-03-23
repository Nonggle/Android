package com.nonggle.resume.impl.step1

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.nonggle.common.utils.getDateTimeFormatter
import com.nonggle.common.utils.getUserAge
import com.nonggle.ui.BaseViewModel
import com.nonggle.domain.repository.ResumeDraftStoreInterface
import com.nonggle.domain.usecase.ImageContentReadUseCase
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
        viewModelScope.launch {
            when (event) {
                is ResumeStep1Event.SelectImage -> setProfileImageUri(event.imageUri)

                is ResumeStep1Event.ImageVolumeExceeded -> {
                    postEffect(ResumeStep1Effect.SendImageVolumeOverFlowMessage)
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
    }

    private suspend fun setProfileImageUri(imageUri: Uri?) {
        if (imageUri == null) return
        var imageMeta: ResumeWritingModel.ResumeImageMeta? = null
        imageMeta = imageContentReadUseCase(imageUri.toString())

        if (imageMeta == null) return

        updateState {
            copy(
                info = this.info.copy(profileImageUrl = imageUri.toString())
            )
        }
        resumeStore.update { it.copy(imageMeta = imageMeta) }
    }

    private suspend fun removeProfileImageUri() {
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

    private suspend fun userNameChanged(userName: String) {
        updateState { copy(info = this.info.copy(userName = userName)) }
        resumeStore.update {
            it.copy(userName = userName)
        }
    }

    private suspend fun userNameCleared() {
        updateState { copy(info = this.info.copy(userName = "")) }
        resumeStore.update {
            it.copy(userName = "")
        }
    }

    private suspend fun selectGender(gender: Gender) {
        updateState { copy(info = this.info.copy(gender = gender)) }
        resumeStore.update {
            it.copy(gender = gender.value)
        }
    }


    private suspend fun setBirthDate(date: LocalDate) {
        // 생년월일 지정 날짜가 현재를 초과할 수 없으므로 에러 토스트 발행
        if (date.isAfter(LocalDate.now())) {
            postEffect(ResumeStep1Effect.SendBirthDateNotValidMessage)
            return
        }
        updateState {
            copy(
                info = this.info.copy(
                    birthDate = getDateTimeFormatter(date),
                    userAge = "${getUserAge(date)}세"
                ),
            )
        }
        resumeStore.update {
            it.copy(
                birthDate = getDateTimeFormatter(date),
                userAge = "${getUserAge(date)}세"
            )
        }
    }

    private suspend fun existCertification(exist: Boolean) {
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

    private suspend fun removeCertificationChip(id: String) {
        updateState {
            val certificationList = this.info.certificationList.filter { it.id != id }
            copy(info = this.info.copy(certificationList = certificationList))
        }
        resumeStore.update {
            it.copy(certificationList = currentState.info.certificationList.map { it.certificationTitle })
        }
    }

    private suspend fun addCertification() {
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
