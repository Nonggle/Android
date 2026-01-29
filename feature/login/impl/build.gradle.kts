plugins {
    alias(libs.plugins.example.nonggle.android.feature.ui)
    alias(libs.plugins.example.nonggle.android.serialization)
    alias(libs.plugins.example.nonggle.android.detekt)
}

android {
    namespace = "com.example.impl"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // kakao login
    implementation(libs.kakao.user)

    implementation(project(":core:ui"))
    implementation(project(":feature:login:api"))
}