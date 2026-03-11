plugins {
    alias(libs.plugins.nonggle.module.jvm.library)
    alias(libs.plugins.nonggle.android.detekt)
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.coroutines.core)
    implementation(libs.javax.inject)
}