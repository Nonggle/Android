plugins {
    alias(libs.plugins.example.nonggle.android.library)
    alias(libs.plugins.example.nonggle.android.hilt)
    alias(libs.plugins.example.nonggle.android.detekt)
}

android {
    namespace = "com.nonggle.auth"
}

dependencies {
    implementation(libs.google.tink.android)
    androidTestImplementation(libs.androidx.junit.ktx)
}