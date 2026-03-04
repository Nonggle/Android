package com.nonggle.network.model.resume

import com.nonggle.model.SingleResume
import kotlinx.serialization.Serializable

@Serializable
data class ResumeDto(
    val id: Long,
    val userId: Long,
    val userName: String,
    val userAge: String?,
    val birthDate: String?,
    val userGender: String?,
    val certificationList: List<String>?,
    val careerList: List<CareerResponseData>,
    val totalCareer: String,
    val introduce: String?,
    val introduceDetail: String?,
    val personalityList: List<String>?,
    val profileImageUrl: String?,
    val createdAt: String?,
    val updatedAt: String?,
) {
    @Serializable
    data class CareerResponseData(
        val careerStartDate: String,
        val careerEndDate: String,
        val careerPeriod: String?,
        val careerDescription: String,
        val careerDetail: String
    )
}

fun ResumeDto.asExternalModel(): SingleResume =
    SingleResume(
        id = id,
        userName = userName,
        userAge = userAge ?: "",
        gender = userGender ?: "",
        certificationList = certificationList ?: emptyList(),
        careerList = careerList.map { career ->
            SingleResume.Career(
                careerStartDate = career.careerStartDate,
                careerEndDate = career.careerEndDate,
                careerPeriod = career.careerPeriod ?: "기간없음",
                careerDescription = career.careerDescription,
                careerDetail = career.careerDetail
            )
        } ?: emptyList(),
        totalCareer = totalCareer,
        introduce = introduce ?: "",
        introduceDetail = introduceDetail ?: "",
        personalityList = personalityList ?: emptyList(),
        profileImageUrl = profileImageUrl ?: "",
        createdAt = createdAt ?: "",
        updatedAt = updatedAt ?: ""
    )