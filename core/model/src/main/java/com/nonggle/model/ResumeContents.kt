package com.nonggle.model

import java.util.UUID

data class ResumeContents(
    val id: Long,
    val userProfileImageUrl: String,
    val userName: String, // 이름
    val gender: String, // 성별
    val userAge: String,
    val summary: String, // 한줄 요약
    val careerPeriod: String, //경력
    val careerList: List<Career>, //경력 목록
    val certificateList: List<Certificate>, //자격증 목록
    val userDetailKeyword: List<Personality>, //성격 유형들
    val userDetailSummary: String, // 간단 자기소개
) {
    data class Career(
        val id: String = UUID.randomUUID().toString(),
        val title: String, // 경력 제목
        val careerStareDate: String,
        val careerEndDate: String,
        val careerPeriod: String, //전체 경력 기간 x개월
        val careerExplanation: String //경력 사항 내용
    )

    data class Certificate(
        val id: String = UUID.randomUUID().toString(),
        val certificateTitle: String //자격증 세부항목
    )

    data class Personality(
        val id: String = UUID.randomUUID().toString(),
        val type: String // 사용자 성격 유형 세부항목
    )
}

