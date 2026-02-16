plugins {
    alias(libs.plugins.example.nonggle.android.feature.ui)
    alias(libs.plugins.example.nonggle.android.serialization)
    alias(libs.plugins.example.nonggle.android.detekt)
}

android {
    namespace = "com.example.feature.resume.impl"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.lottie.compose)

    implementation(project(":core:designsystem"))
    implementation(project(":feature:resume:api"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
}