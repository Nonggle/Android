plugins {
    `kotlin-dsl`
}

group = "com.example.buildlogic"


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "example.nonggle.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "example.nonggle.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "example.nonggle.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "example.nonggle.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidFeatureUI") {
            id = "example.nonggle.android.feature.ui"
            implementationClass = "AndroidFeatureUIConventionPlugin"
        }

        register("jvmLibrary") {
            id = "example.nonggle.module.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "example.nonggle.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidSerialization") {
            id = "example.nonggle.android.serialization"
            implementationClass = "AndroidSerializationConventionPlugin"
        }
        register("androidDetekt") {
            id = "example.nonggle.android.detekt"
            implementationClass = "AndroidDetektConventionPlugin"
        }
    }
}
