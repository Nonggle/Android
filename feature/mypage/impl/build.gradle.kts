plugins {
    alias(libs.plugins.nonggle.android.feature.ui)
    alias(libs.plugins.nonggle.android.serialization)
    alias(libs.plugins.nonggle.android.detekt)
}

android {
    namespace = "com.nonggle.mypage.impl"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":feature:mypage:api"))
}