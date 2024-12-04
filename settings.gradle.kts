pluginManagement {
    includeBuild("build-logic")

    repositories {
        google()
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

rootProject.name = "RegionPerformanceManager"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:common")
include(":core:log")
include(":core:network")
include(":core:auth")
include(":core:ui")
include(":core:database")
include(":core:datastore")
include(":core:datastore-proto")
include(":core:preference")
include(":core:designsystem")
include(":core:notification")

include(":settings")
include(":user")
