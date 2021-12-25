import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation

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
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT") // Paper
    shadow("net.kyori:adventure-text-minimessage:4.2.0-SNAPSHOT") { // Minimessage
        exclude("net.kyori", "adventure-api")
    }
//    shadow("org.spongepowered:configurate-yaml:4.1.2") { // Configurate
//        exclude("org.yaml.snakeyaml")
//    }
}

tasks {

    runServer {
        minecraftVersion("1.18.1")
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