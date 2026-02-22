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