pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()

    }
}
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
    }
}
rootProject.name = "MVIModularizationTemplate"
include(":app")
include(":common:models")
include(":core:network")
