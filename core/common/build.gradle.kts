plugins {
    alias(libs.plugins.nonggle.android.library)
    alias(libs.plugins.nonggle.android.hilt)
    alias(libs.plugins.nonggle.android.detekt)
}

android {
    namespace = "com.nonggle.core.common"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
}