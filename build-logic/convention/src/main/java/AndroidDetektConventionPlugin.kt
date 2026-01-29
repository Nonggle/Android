
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidDetektConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // 1. 플러그인 적용
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            // 2. Detekt 설정
            val detektExtension = extensions.getByType<DetektExtension>()
            configureDetekt(detektExtension)

            // 3. Detekt Formatting 플러그인 추가 (ktlint 규칙)
            dependencies {
                "detektPlugins"("io.gitlab.arturbosch.detekt:detekt-formatting:${detektExtension.toolVersion}")
            }
        }
    }

    private fun Project.configureDetekt(extension: DetektExtension) = extension.apply {
        // 기본 설정 사용 (빌드 시 룰셋 상속)
        buildUponDefaultConfig = true
        // 설정 파일 위치 (루트 프로젝트 기준)
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        source.setFrom(
            files(
                "src/main/java"
            )
        )
        // 병렬 처리
        parallel = true
        // 자동 수정 활성화
        autoCorrect = true
    }
}