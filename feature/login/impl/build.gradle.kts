plugins {
    alias(libs.plugins.example.nonggle.android.feature.ui)
    alias(libs.plugins.example.nonggle.android.serialization)
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
    implementation("com.kakao.sdk:v2-user:2.11.0")

    implementation(project(":feature:login:api"))
}