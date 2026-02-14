plugins {
    alias(libs.plugins.example.nonggle.android.feature.ui)
    alias(libs.plugins.example.nonggle.android.hilt)
    alias(libs.plugins.example.nonggle.android.serialization)
    alias(libs.plugins.example.nonggle.android.detekt)
}

android {
    namespace = "com.example.feature.login.impl"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // kakao login
    implementation(libs.kakao.user)

    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":feature:login:api"))
}