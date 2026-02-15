package com.nonggle.model

data class ResumeContents(
    val userProfileImageUrl: String,
    val userName: String, // 이름
    val gender: String, // 성별
    val summary: String, // 한줄 요약
    val careerPeriod: String, //경력
    val careerList: List<Career>, //경력 목록
    val certificateList: List<Certificate>, //자격증 목록
    val userDetailKeyword: List<Personality>, //성격 유형들
    val userDetailSummary: String, // 간단 자기소개
) {
    data class Career(
        val title: String, // 경력 제목
        val period: String, // 경력 기간 2xxx.xx.xx ~ 2xxx.xx.xx
        val periodTotal: String, //전체 경력 기간 x개월
        val careerExplanation: String, //경력 사항 내용
    )

    data class Certificate(
        val certificateTitle: String //자격증 세부항목
    )

    data class Personality(
        val type: String // 사용자 성격 유형 세부항목
    )
}
