package com.example.feature.resume.impl.step1

import android.net.Uri
import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState

enum class Gender(val value: Int) {
    MALE(0), FEMALE(1);

    companion object {
        private val map = entries.associateBy(Gender::value)
        fun getByValue(value: Int): Gender? {
            return map[value]
        }
    }
}

data class InfoData(
    val profileImageUrl: Uri? = null,
    val userName: String = "",
    val birthDate: String = "",
    val introduction: String? = null,
    val gender: Gender? = null,
    val certificationList: List<String> = emptyList()
)

data class ResumeStep1State(
    val isLoading: Boolean = true,
    val certificationExist: Boolean? = null,
    val certificationInput: String = "",
    val info: InfoData = InfoData()
) : UiState

sealed interface ResumeStep1Event : UiEvent {
    data class SelectImage(val imageUri: Uri): ResumeStep1Event

    data class UserNameChanged(val userName: String): ResumeStep1Event
    data object UserNameCleared: ResumeStep1Event
    data object RemoveProfileImage: ResumeStep1Event

    data class BirthDateChanged(val date: String): ResumeStep1Event

    data class SelectGender(val gender: Gender): ResumeStep1Event

    data class ExistCertification(val exist: Boolean): ResumeStep1Event

    data class CertificationChanged(val certification: String): ResumeStep1Event

    data object AddCertification: ResumeStep1Event

    data object ClearCertification: ResumeStep1Event
}

sealed interface ResumeStep1Effect : UiEffect {

}