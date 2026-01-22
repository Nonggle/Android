import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.example.nonggle.android.application.compose)
    alias(libs.plugins.example.nonggle.android.hilt)
}

fun getSecretKey(key: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(key)
}

android {
    namespace = "com.example.nonggleresume"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"${getSecretKey("KAKAO_NATIVE_APP_KEY")}\"")
        manifestPlaceholders["kakaoKey"] = "kakao${getSecretKey("KAKAO_NATIVE_APP_KEY")}"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    api(libs.androidx.compose.runtime)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    //Navigation3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)

    // kakao login
    implementation("com.kakao.sdk:v2-user:2.11.0")


    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))

    implementation(project(":feature:login:impl"))
    implementation(project(":feature:login:api"))
    implementation(project(":feature:home"))
    implementation(project(":feature:download"))
    implementation(project(":feature:setting"))
}