plugins {
    alias(libs.plugins.example.nonggle.android.library.compose)
    alias(libs.plugins.example.nonggle.android.detekt)
}

android {
    namespace = "com.nonggle.pdf_render"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
