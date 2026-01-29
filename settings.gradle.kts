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
        gradlePluginPortal {
            content {
                // Add this line to include the KSP plugin
                includeGroupByRegex("com\\.google\\.devtools\\.ksp.*")
            }
        }
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

include(":feature:home")
include(":feature:download")
include(":feature:setting")
include(":feature:login:impl")
include(":feature:login:api")
include(":core:ui")
include(":feature:resume:impl")
include(":feature:resume:api")
