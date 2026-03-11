plugins {
    alias(libs.plugins.nonggle.android.library)
    alias(libs.plugins.nonggle.android.hilt)
    alias(libs.plugins.nonggle.android.detekt)
}

android {
    namespace = "com.nonggle.auth"
}

dependencies {
    implementation(libs.google.tink.android)
    androidTestImplementation(libs.androidx.junit.ktx)
    ksp(libs.hilt.compiler)

    implementation(project(":core:common"))
}