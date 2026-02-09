plugins {
    alias(libs.plugins.example.nonggle.android.library)
    alias(libs.plugins.example.nonggle.android.hilt)
    alias(libs.plugins.example.nonggle.android.serialization)
    alias(libs.plugins.example.nonggle.android.detekt)
}

android {
    namespace = "com.example.core.network"
}

dependencies {
    // Ktor 클라이언트 핵심 라이브러리
    implementation(libs.ktor.client.core)

    // [수정 5] 안드로이드 환경에 더 적합한 Ktor 엔진으로 변경합니다. (cio도 사용 가능하지만 android가 일반적)
    implementation(libs.ktor.client.android)

    // JSON 등 콘텐츠 협상을 위한 라이브러리
    implementation(libs.ktor.client.content.negotiation)

    // kotlinx.serialization을 사용하여 JSON을 처리하기 위한 라이브러리
    implementation(libs.ktor.serialization.kotlinx.json)

    // (선택사항, 강력 추천) 로깅 라이브러리
    implementation(libs.ktor.client.logging)

    // [수정 6] Hilt 관련 라이브러리 의존성을 추가합니다.
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}