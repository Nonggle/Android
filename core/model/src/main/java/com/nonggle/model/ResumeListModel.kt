package com.nonggle.model

data class SingleResume(
    val id: Long = 0,
    val userName: String = "",
    val introduction: String = "",
    val gender: String = "",
    val certificationList: List<String> = emptyList(),
    val careerList: List<Career> = emptyList(),
    val totalCareer: String = "",
    val introduce: String = "",
    val introduceDetail: String = "",
    val personalityList: List<String> = emptyList(),
    val profileImageUrl: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
) {
    data class Career(
        val careerStartDate: String = "",
        val careerEndDate: String = "",
        val careerDescription: String = "",
        val careerDetail: String = "",
    )
}

fun SingleResume.toResumeContents(): ResumeContents =
    ResumeContents(
        id = id,
        userProfileImageUrl = profileImageUrl,
        userName = userName,
        gender = gender,
        userAge = "",
        summary = introduction,
        careerPeriod = totalCareer,
        careerList = careerList.map { career ->
            ResumeContents.Career(
                title = career.careerDescription,
                period = career.careerStartDate,
                periodTotal = career.careerEndDate,
                careerExplanation = career.careerDetail
            )
        },
        certificateList = certificationList.map { cert ->
            ResumeContents.Certificate(certificateTitle = cert)
        },
        userDetailKeyword = personalityList.map { personality ->
            ResumeContents.Personality(type = personality)
        },
        userDetailSummary = introduce
    )