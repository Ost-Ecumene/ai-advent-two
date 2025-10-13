pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven {
            url = uri("https://androidx.dev/snapshots/builds/13994858/artifacts/repository")
        }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://androidx.dev/snapshots/builds/13994858/artifacts/repository")
        }
    }
}

rootProject.name = "AI_Advent_2"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
includeBuild("build-logic")
include(":app")
include(":core:common")
include(":core:design-system")
include(":core:domain")
include(":core:navigation")
include(":core:ui")
include(":core:mcp")
include(":feature:chat")
include(":feature:mcp")
