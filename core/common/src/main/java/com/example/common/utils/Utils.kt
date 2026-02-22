package com.example.common.utils

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter


fun getDateTimeFormatter(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    return date.format(formatter)
}

fun getPeriodFormatter(period: Period): String {
    return "${period.years}년 ${period.months}개월 ${period.days}일"
}