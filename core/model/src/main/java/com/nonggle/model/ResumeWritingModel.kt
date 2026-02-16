package com.nonggle.model

import java.time.LocalDate

data class ResumeWritingModel(
    val file: ByteArray,
    val fileName: String,
    val mimeType: String = "image/jpeg",
    val userName: String,
    val birthDate: LocalDate,
    val introduction: String,
    val gender: String,
    val certificationList: List<Certification>,
    val careerList: List<Career>,
    val totalCareer: String,
    val introduceDetail: String,
    val personalityList: List<Personality>,
    val introduce: String,
) {
    data class Certification(
        val certificationTitle: String,
    )

    data class Career(
        val careerStartDate: LocalDate,
        val careerEndDate: LocalDate,
        val careerDescription: String,
        val careerDetail: String
    )

    data class Personality(
        val personality: String,
    )
}