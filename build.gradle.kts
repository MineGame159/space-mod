@file:Suppress("PropertyName")

import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    java
    id("dev.architectury.loom") version "1.11-SNAPSHOT" apply false
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("com.gradleup.shadow") version "8.3.6" apply false
}

val mod_group: String by project
val mod_id: String by project
val mod_version: String by project
val minecraft_version: String by project
val parchment_version: String by project

architectury {
    minecraft = minecraft_version
}

allprojects {
    group = mod_group
    version = mod_version
}

subprojects {
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "architectury-plugin")

    base {
        archivesName.set("$mod_id-${project.name}-$minecraft_version")
    }

    val loom = extensions.getByType<LoomGradleExtensionAPI>()

    configure<LoomGradleExtensionAPI> {
        silentMojangMappingsLicense()
    }

    repositories {
        maven {
            name = "ParchmentMC"
            url = uri("https://maven.parchmentmc.org")
        }
    }

    dependencies {
        "minecraft"("net.minecraft:minecraft:$minecraft_version")

        "mappings"(loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-$minecraft_version:$parchment_version@zip")
        })
    }

    java {
        withSourcesJar()

        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<JavaCompile> {
        options.release = 21
    }

    idea {
        module {
            isDownloadSources = true
            isDownloadJavadoc = true
        }
    }
}
