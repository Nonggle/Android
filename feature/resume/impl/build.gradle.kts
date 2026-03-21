plugins {
    alias(libs.plugins.nonggle.android.feature.ui)
    alias(libs.plugins.nonggle.android.serialization)
    alias(libs.plugins.nonggle.android.detekt)
}

android {
    namespace = "com.nonggle.feature.resume.impl"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.coroutines.test)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.lottie.compose)

    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))

    implementation(project(":feature:resume:api"))
    implementation(project(":feature:resume_view:api"))
}