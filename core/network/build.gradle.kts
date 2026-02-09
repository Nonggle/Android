plugins {
    alias(libs.plugins.example.nonggle.module.jvm.library)
    kotlin("plugin.serialization")
    alias(libs.plugins.example.nonggle.android.detekt)
}

dependencies {
    // Ktor 클라이언트 핵심 라이브러리
    implementation(libs.ktor.client.core)

    // 순수 코틀린으로 작성된 비동기 클라이언트 엔진 (JVM 환경에 적합)
    implementation(libs.ktor.client.cio)

    // JSON 등 콘텐츠 협상을 위한 라이브러리
    implementation(libs.ktor.client.content.negotiation)

    // kotlinx.serialization을 사용하여 JSON을 처리하기 위한 라이브러리
    implementation(libs.ktor.serialization.kotlinx.json)

    // (선택사항, 강력 추천) 로깅 라이브러리
    implementation(libs.ktor.client.logging)
}