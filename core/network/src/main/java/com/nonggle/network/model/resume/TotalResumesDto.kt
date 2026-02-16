package com.nonggle.network.model.resume

import kotlinx.serialization.Serializable

@Serializable
data class TotalResumesDto (
    val resumes: List<ResumeDto>,
) {
    @Serializable
    data class ResumeDto (
        val userId: Long,
        val userName: String,
        val introduction: String,
        val gender: String,
        val certificationList: List<String>,
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