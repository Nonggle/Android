plugins {
    `kotlin-dsl`
}

group = "com.nonggle.buildlogic"


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
            id = "nonggle.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "nonggle.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "nonggle.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "nonggle.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidFeatureUI") {
            id = "nonggle.android.feature.ui"
            implementationClass = "AndroidFeatureUIConventionPlugin"
        }

        register("jvmLibrary") {
            id = "nonggle.module.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "nonggle.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidSerialization") {
            id = "nonggle.android.serialization"
            implementationClass = "AndroidSerializationConventionPlugin"
        }
        register("androidDetekt") {
            id = "nonggle.android.detekt"
            implementationClass = "AndroidDetektConventionPlugin"
        }
    }
}
