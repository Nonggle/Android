package com.nonggle.network.model.resume

import com.nonggle.model.SingleResume
import kotlinx.serialization.Serializable

@Serializable
data class ResumeDto(
    val id: Long,
    val userId: Long,
    val userName: String,
    val birthDate: String?,
    val introduction: String?,
    val gender: String?,
    val certificationList: List<String>?,
    val careerList: List<CareerResponseData>?,
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
        val careerDescription: String,
        val careerDetail: String
    )
}

fun ResumeDto.asExternalModel(): SingleResume =
    SingleResume(
        userName = userName,
        introduction = introduction ?: "",
        gender = gender ?: "",
        certificationList = certificationList ?: emptyList(),
        careerList = careerList?.map { career ->
            SingleResume.Career(
                careerStartDate = career.careerStartDate,
                careerEndDate = career.careerEndDate,
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