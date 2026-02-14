import com.example.convention.hiltExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidHiltConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }
            dependencies {
                hiltExtension(target)
            }
        }
    }
}