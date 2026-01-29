plugins {
    alias(libs.plugins.example.nonggle.android.library.compose)
    alias(libs.plugins.example.nonggle.android.detekt)
}

android {
    namespace = "com.example.designsystem"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
}