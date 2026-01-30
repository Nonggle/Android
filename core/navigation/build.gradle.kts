import org.gradle.kotlin.dsl.dependencies

plugins {
    alias(libs.plugins.example.nonggle.android.library)
    alias(libs.plugins.example.nonggle.android.library.compose)
    alias(libs.plugins.example.nonggle.android.detekt)
}

android {
    namespace = "com.example.core.navigation"
}

dependencies {
    api(libs.androidx.compose.runtime)
    // navigation3
    implementation(libs.androidx.navigation3.runtime)
    //ViewModel이 백 스택의 항목으로 범위가 지정되도록 허용합니다.
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
    implementation(libs.androidx.savedstate.compose)
}