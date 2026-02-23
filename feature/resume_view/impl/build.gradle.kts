plugins {
    alias(libs.plugins.example.nonggle.android.feature.ui)
    alias(libs.plugins.example.nonggle.android.serialization)
    alias(libs.plugins.example.nonggle.android.detekt)
}

android {
    namespace = "com.nonggle.feature.resume_view.impl"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    //ViewModel이 백 스택의 항목으로 범위가 지정되도록 허용합니다.
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(project(":core:ui"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))

    implementation(project(":feature:resume_view:api"))
}