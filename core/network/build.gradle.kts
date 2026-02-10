plugins {
    alias(libs.plugins.example.nonggle.module.jvm.library)
    alias(libs.plugins.example.nonggle.android.detekt)
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.auth)
}
