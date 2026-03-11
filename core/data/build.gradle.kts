plugins {
    alias(libs.plugins.nonggle.android.library)
    alias(libs.plugins.nonggle.android.hilt)
    alias(libs.plugins.nonggle.android.detekt)
}

android {
    namespace = "com.nonggle.core.data"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:auth"))
    implementation(project(":core:network"))
}