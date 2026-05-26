pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    includeBuild("gradle/build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

gradle.settingsEvaluated {
    fun RepositoryHandler.kobwebSnapshots() {
        maven("https://central.sonatype.com/repository/maven-snapshots/") {
            mavenContent {
                includeGroupByRegex("com\\.varabyte\\.kobweb.*")
                snapshotsOnly()
            }
        }
    }

    pluginManagement.repositories { kobwebSnapshots() }
    dependencyResolutionManagement.repositories { kobwebSnapshots() }
}

rootProject.name = "jangalinski.github.io"
include("app", "site")
