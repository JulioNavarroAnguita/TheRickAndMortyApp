pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("gradle/versionCatalogs/libs.versions.toml"))
        }
    }
}

rootProject.name = "TheRickAndMortyApp"
include(":app")
include(":data-layer")
include(":domain-layer")
include(":presentation-layer")
