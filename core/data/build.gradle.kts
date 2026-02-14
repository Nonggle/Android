plugins {
    alias(libs.plugins.example.nonggle.android.library)
    alias(libs.plugins.example.nonggle.android.hilt)
    alias(libs.plugins.example.nonggle.android.detekt)
}

android {
    namespace = "com.example.core.data"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
}