plugins {
    alias(libs.plugins.example.nonggle.android.feature.ui)
    alias(libs.plugins.example.nonggle.android.serialization)
}

android {
    namespace = "com.example.api"
}

dependencies {
    implementation(project(":feature:login:impl"))
}