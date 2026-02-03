package com.example.feature.resume.impl.step1

import android.net.Uri
import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ResumeStep1ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep1Event, ResumeStep1State, ResumeStep1Effect>(initialState = ResumeStep1State()) {
    override fun onEvent(event: ResumeStep1Event) {
        when (event) {
            is ResumeStep1Event.SelectImage -> selectUserProfileImage(event.imageUri)
            is ResumeStep1Event.UserNameChanged -> userNameChanged(event.userName)
            is ResumeStep1Event.UserNameCleared -> userNameChanged("")
            is ResumeStep1Event.RemoveProfileImage -> removeProfileImage()
            is ResumeStep1Event.BirthDateChanged -> birthDateChanged(event.date)
            is ResumeStep1Event.SelectGender -> selectGender(event.gender)
            is ResumeStep1Event.ExistCertification -> existCertification(event.exist)
            is ResumeStep1Event.AddCertification -> addCertification()
            is ResumeStep1Event.CertificationChanged -> certificationChanged(event.certification)
            is ResumeStep1Event.RemoveCertificationChip -> removeCertificationChip(event.id)
        }
    }

    private fun selectUserProfileImage(imageUri: Uri) {
        updateState { copy(info = this.info.copy(profileImageUrl = imageUri)) }
    }

    private fun userNameChanged(userName: String) {
        updateState { copy(info = this.info.copy(userName = userName)) }
    }

    private fun removeProfileImage() {
        updateState { copy(info = this.info.copy(profileImageUrl = null)) }
    }

    private fun birthDateChanged(date: String) {
        updateState { copy(info = this.info.copy(birthDate = date)) }
    }

    private fun selectGender(gender: Gender) {
        updateState { copy(info = this.info.copy(gender = gender)) }
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

    private fun certificationChanged(certificate: String) {
        updateState { copy(certificationInput = certificate) }
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
            val certificationData = CertificationData(
                certificationTitle = certificationInput.trim()
            )

            copy(
                info = info.copy(certificationList = info.certificationList + certificationData),
                certificationInput = ""
            )
        }
    }

}
