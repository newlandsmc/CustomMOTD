import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import java.io.ByteArrayOutputStream

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

group = "com.semivanilla.custommotd"
version = "1.0.0-SNAPSHOT"
description = "Plugin allows staff to easily manage the MOTD displayed for the server in users' server list."

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT") // Paper
}

tasks {

    runServer {
        minecraftVersion("1.20.1")
    }

    shadowJar {
        dependsOn(getByName("relocateJars") as ConfigureShadowRelocation)
        archiveFileName.set("${project.name}-${project.version}.jar")
        minimize()
        configurations = listOf(project.configurations.shadow.get())
    }

    build {
        dependsOn(shadowJar)
    }

    create<ConfigureShadowRelocation>("relocateJars") {
        target = shadowJar.get()
        prefix = "${project.name}.lib"
    }
}

bukkit {
    name = rootProject.name
    main = "$group.CustomMOTD"
    version = "${rootProject.version}-${gitCommit()}"
    apiVersion = "1.18"
    website = "https://github.com/SemiVanilla-MC/CustomMOTD"
    authors = listOf("destro174")
    commands {
        create("custommotd") {
            description = "Base command for the custommotd plugin."
            usage = "/custommotd help"
            permission = "custommotd.command"
        }
    }
}

fun gitCommit(): String {
    val os = ByteArrayOutputStream()
    project.exec {
        commandLine = "git rev-parse --short HEAD".split(" ")
        standardOutput = os
    }
    return String(os.toByteArray()).trim()
}