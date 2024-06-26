pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":app")
include(":utils")
include(":module-injector")
include(":common")
include(":movies:core")
include(":movies:feature-popular")
include(":shared-ui")
include(":movies:list")
include(":network")
include(":database")
include(":movies:feature-favourite")
