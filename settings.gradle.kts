pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "NonggleResume"
include(":app")

// core 모듈
include(":core:data")
include(":core:designsystem")
include(":core:navigation")
include(":core:domain")
include(":core:common")
include(":core:pdf_render")
include(":core:network")
include(":core:auth")
include(":core:model")
include(":core:ui")

include(":feature:login:impl")
include(":feature:login:api")
include(":feature:resume:impl")
include(":feature:resume:api")
include(":feature:home:api")
include(":feature:home:impl")
include(":feature:resume_view:api")
include(":feature:resume_view:impl")
include(":feature:download:api")
include(":feature:download:impl")
include(":feature:mypage:api")
include(":feature:mypage:impl")
