pluginManagement {
    repositories {
        maven { url = uri("https://androidx.dev/snapshots/builds/11514708/artifacts/repository/") }
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
        maven { url = uri("https://androidx.dev/snapshots/builds/11514708/artifacts/repository/") }
        google()
        mavenCentral()
    }
}

rootProject.name = "sandbox over again"
include(":app")
include(":mylibrary")
include(":mylibrary-sdk-bundle")
include(":mylibrary-runtime-sdk")
