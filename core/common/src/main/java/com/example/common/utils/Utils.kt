package com.example.common.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/// 날짜 포맷팅
fun getDateTimeFormatter(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    return date.format(formatter)
}

// 총 기간의 문자열 파싱에 활용
fun getPeriodFormatter(period: Period): String {
    return "${period.years}년 ${period.months}개월 ${period.days}일"
}
// 시작~ 종료 기간의 문자열 파싱에 활용
fun getWorkPeriodFormatter(startDate: String, endDate: String): String {
    return "${startDate} ~ ${endDate}"
}

/// 이미지 파일 크기 측정용
fun getImageSizeFromUri(context: Context, uri: Uri): Long {
    val projection = arrayOf(OpenableColumns.SIZE)
    var fileSize: Long = 0
    context.contentResolver.query(uri, projection, null, null,null)?.use { cursor ->
        // SIZE 열의 인덱스를 찾음
        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
        // 커서를 첫 번째 행으로 이동
        if (cursor.moveToFirst()) {
            // 파일 크기 가져오기
            fileSize = cursor.getLong(sizeIndex)
        }
    }
    return fileSize
}

// 생년월일로부터 나이 측정에 활용
fun getUserAge(date: LocalDate): Int {
    return ChronoUnit.YEARS.between(date, LocalDate.now()).toInt()
}

// 문자열 리스트 단일 문장으로 파싱에 활용
fun combineListToString(list: List<String>): String {

}

