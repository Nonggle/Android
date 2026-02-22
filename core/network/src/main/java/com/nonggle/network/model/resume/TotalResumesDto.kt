package com.nonggle.network.model.resume

import com.nonggle.model.ResumeListModel
import com.nonggle.model.ResumeListModel.SingleResume
import kotlinx.serialization.Serializable

@Serializable
data class TotalResumesDto(
    val resumes: List<ResumeDto>,
) {
    @Serializable
    data class ResumeDto(
        val userId: Long,
        val userName: String,
        val introduction: String,
        val gender: String,
        val certificationList: List<String>?,
        val careerList: List<CareerResponseData>,
        val totalCareer: String,
        val introduce: String,
        val introduceDetail: String,
        val personalityList: List<String>,
        val profileImageUrl: String,
        val createdAt: String,
        val updatedAt: String,
    ) {
        @Serializable
        data class CareerResponseData(
            val careerStartDate: String,
            val careerEndDate: String,
            val careerDescription: String,
            val careerDetail: String
        )
    }
}

fun TotalResumesDto.asExternalModel(): ResumeListModel =
    ResumeListModel(
        resumes = resumes.map {
            SingleResume(
                userName = it.userName,
                introduction = it.introduction,
                gender = it.gender,
                certificationList = it.certificationList ?: emptyList(),
                careerList = it.careerList.map { career ->
                    SingleResume.Career(
                        careerStartDate = career.careerStartDate,
                        careerEndDate = career.careerEndDate,
                        careerDescription = career.careerDescription,
                        careerDetail = career.careerDetail
                    )
                }
            )
        })