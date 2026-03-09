plugins {
    alias(libs.plugins.example.nonggle.module.jvm.library)
    alias(libs.plugins.example.nonggle.android.detekt)
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.coroutines.core)
    implementation(libs.javax.inject)
}