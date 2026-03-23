plugins {
    alias(libs.plugins.nonggle.android.library)
    alias(libs.plugins.nonggle.android.serialization)
    alias(libs.plugins.nonggle.android.detekt)
}

android {
    namespace = "com.nonggle.mypage.api"
}

dependencies {
    implementation(libs.navigation3.runtime)
}