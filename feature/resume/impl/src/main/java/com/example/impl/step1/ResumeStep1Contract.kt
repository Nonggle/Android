package com.example.feature.resume.impl.step1

import android.net.Uri
import androidx.compose.runtime.Stable
import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState
import java.time.LocalDate
import java.util.UUID

enum class Gender(val value: String) {
    MALE("MALE"), FEMALE("FEMALE");

    companion object {
        private val map = entries.associateBy(Gender::value)
        fun getByValue(value: String): Gender? {
            return map[value]
        }
    }
}

@Stable
data class CertificationTag(
    val id: String = UUID.randomUUID().toString(),
    val certificationTitle: String
)

data class InfoData(
    val profileImageUrl: String? = null,
    val userName: String = "",
    val birthDate: LocalDate? = null,
    val introduction: String? = null,
    val gender: Gender? = null,
    val certificationList: List<CertificationTag> = emptyList()
)

data class ResumeStep1State(
    val certificationExist: Boolean? = null,
    val certificationInput: String = "",
    val birthDate: String = "",
    val info: InfoData = InfoData()
) : UiState

sealed interface ResumeStep1Event : UiEvent {
    data class SelectImage(val imageUri: Uri?): ResumeStep1Event

    data class ImageVolumeExceeded(val message: String): ResumeStep1Event

    data class UserNameChanged(val userName: String): ResumeStep1Event
    data object UserNameCleared: ResumeStep1Event
    data object RemoveProfileImage: ResumeStep1Event

    data class BirthDateChanged(val birthDate: LocalDate): ResumeStep1Event

    data class SelectGender(val gender: Gender): ResumeStep1Event

    data class ExistCertification(val exist: Boolean): ResumeStep1Event

    data class CertificationChanged(val certification: String): ResumeStep1Event

    data object AddCertification: ResumeStep1Event

    data class RemoveCertificationChip(val id: String): ResumeStep1Event
}

sealed interface ResumeStep1Effect : UiEffect {
    data class SendToastMessage(val message: String): ResumeStep1Effect
}