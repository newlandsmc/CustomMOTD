rootProject.name = "CustomMOTD"

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        maven { // Paper
            url = uri("https://papermc.io/repo/repository/maven-public/")
        }
        maven { // Configurate
            url = uri("https://repo.spongepowered.org/maven")
        }
        maven { // run paper plugin
            url = uri("https://repo.jpenilla.xyz/snapshots/")
        }
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

