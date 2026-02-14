package com.example.convention
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.LibraryExtension
import org.gradle.kotlin.dsl.configure
import java.util.Properties

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        when(extensionType) {
            ExtensionType.APPLICATION -> {
                val debugUrl = project.readLocalProperty("DEBUG_API_URL") ?: "http://10.0.2.2:8080"
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(debugUrl)
                        }
                        release {
                            configureReleaseBuildType(commonExtension)
                        }
                    }
                }
            }
            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    val debugUrl = project.readLocalProperty("DEBUG_API_URL") ?: "http://10.0.2.2:8080"
                    buildTypes {
                        debug {
                            configureDebugBuildType(debugUrl)
                        }
                        release {
                            configureReleaseBuildType(commonExtension)
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(baseUrl: String) {
    buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
    buildConfigField("String", "VERSION_NAME", "\"1.0.0\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    buildConfigField("String", "BASE_URL", "\"RELEASE_API_URL\"")
    buildConfigField("String", "VERSION_NAME", "\"1.0.0\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}

fun Project.readLocalProperty(key: String): String? {
    val props = Properties()
    val file = rootProject.file("local.properties")
    if (!file.exists()) return null
    file.inputStream().use { props.load(it) }
    return props.getProperty(key)?.trim()
}