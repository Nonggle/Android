plugins {
    alias(libs.plugins.example.nonggle.android.library)
    alias(libs.plugins.example.nonggle.android.hilt)
    alias(libs.plugins.example.nonggle.android.detekt)
    alias(libs.plugins.example.nonggle.android.serialization)
}

android {
    namespace = "com.nonggle.network"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.auth)
    testImplementation(libs.junit.junit)

    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.io.mockk.mockk)
    testImplementation(libs.coroutines.test)

    implementation(project(":core:auth"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))
}
