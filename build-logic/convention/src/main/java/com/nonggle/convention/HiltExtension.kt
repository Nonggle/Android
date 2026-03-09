package com.nonggle.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope


fun DependencyHandlerScope.hiltExtension(project: Project) {
    add("implementation", project.libs.findLibrary("hilt.android").get())
    add("implementation", project.libs.findLibrary("hilt.navigation.compose").get())
    add("ksp", project.libs.findLibrary("hilt.compiler").get())
}