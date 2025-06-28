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
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Tiktok"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":common:ui")
include(":core")
include(":data")
include(":feature:inbox")
include(":feature:authentication")
include(":feature:friends")
include(":feature:myprofile")
include(":common:theme")
include(":feature:settings")
include(":feature:cameramedia")
include(":feature:home")
include(":feature:comment")
include(":feature:creatorprofile")
include(":feature:loginwithemailphone")
