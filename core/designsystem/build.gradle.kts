plugins {
    alias(libs.plugins.nonggle.android.library.compose)
    alias(libs.plugins.nonggle.android.detekt)
}

android {
    namespace = "com.nonggle.core.designsystem"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
}