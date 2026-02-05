package com.example.feature.resume.impl.step1

import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ResumeStep1ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep1Event, ResumeStep1State, ResumeStep1Effect>(initialState = ResumeStep1State()) {
    override fun onEvent(event: ResumeStep1Event) {
        when (event) {
            is ResumeStep1Event.SelectImage -> updateState { copy(info = this.info.copy(profileImageUrl = event.imageUri)) }
            is ResumeStep1Event.UserNameChanged ->  updateState { copy(info = this.info.copy(userName = event.userName)) }
            is ResumeStep1Event.UserNameCleared ->  updateState { copy(info = this.info.copy(userName = "")) }
            is ResumeStep1Event.RemoveProfileImage ->  updateState { copy(info = this.info.copy(profileImageUrl = null)) }
            is ResumeStep1Event.BirthDateChanged -> setBirthDate(event.birthDate)
            is ResumeStep1Event.SelectGender ->  updateState { copy(info = this.info.copy(gender = event.gender)) }
            is ResumeStep1Event.ExistCertification -> existCertification(event.exist)
            is ResumeStep1Event.AddCertification -> addCertification()
            is ResumeStep1Event.CertificationChanged -> updateState { copy(certificationInput = event.certification) }
            is ResumeStep1Event.RemoveCertificationChip -> removeCertificationChip(event.id)
        }
    }


    private fun setBirthDate(date: LocalDate) {
        updateState {
            copy(
                info = this.info.copy(birthDate = date),
                birthDate = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일"
            )
        }
    }

    private fun existCertification(exist: Boolean) {
        if(exist) {
            updateState {
                copy(certificationExist = exist)
            }
        } else {
            updateState {
                copy(certificationExist = exist, info = this.info.copy(certificationList = emptyList()))
            }
        }
    }

    private fun removeCertificationChip(id: String) {
        updateState {
            val certificationList = this.info.certificationList.filter { it.id != id }
            copy(info = this.info.copy(certificationList = certificationList))
        }
    }

    private fun addCertification() {
        updateState {
            if (certificationInput.trim().isEmpty()) return@updateState this
            val certificationTag = CertificationTag(
                certificationTitle = certificationInput.trim()
            )

            copy(
                info = info.copy(certificationList = info.certificationList + certificationTag),
                certificationInput = ""
            )
        }
    }

}
