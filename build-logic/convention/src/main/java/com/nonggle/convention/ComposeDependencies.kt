package com.nonggle.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.addUILayerDependencies(project: Project) {
    add("implementation", project.libs.findBundle("compose").get())
    add("implementation", project(":core:designsystem"))
    add("implementation", project(":core:navigation"))
    add("implementation", project.libs.findLibrary("androidx.navigation3.runtime").get())
    add("debugImplementation", project.libs.findBundle("compose.debug").get())
    add("androidTestImplementation", project.libs.findLibrary("androidx.compose.ui.test.junit4").get())
}