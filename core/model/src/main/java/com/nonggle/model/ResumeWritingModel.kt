package com.nonggle.model

import java.time.LocalDate

data class ResumeWritingModel(
    val imageMeta: ResumeImageMeta = ResumeImageMeta(),
    val userName: String = "",
    val birthDate: String = "",
    val userAge: String = "",
    val gender: String = "",
    val certificationList: List<String>? = null,
    val careerList: List<Career> = listOf(),
    val totalCareer: String = "",
    val introduceDetail: String = "",
    val personalityList: List<String> = listOf(),
    val introduce: String = "",
) {
    data class ResumeImageMeta(
        val uri: String = "",
        val name: String = "",
        val size: Long = -1L,
        val mimeType: String = ""
    )
    data class Career(
        val careerStartDate: LocalDate = LocalDate.now(),
        val careerEndDate: LocalDate = LocalDate.now(),
        val careerPeriod: String = "",
        val careerDescription: String = "",
        val careerDetail: String = "",
    )
}