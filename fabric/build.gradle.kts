@file:Suppress("PropertyName")

plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val commonBundle: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

val shadowBundle: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

configurations {
    compileClasspath.get().extendsFrom(commonBundle)
    runtimeClasspath.get().extendsFrom(commonBundle)
    get("developmentFabric").extendsFrom(commonBundle)
}

val mod_id: String by rootProject
val minecraft_version: String by rootProject
val fabric_loader_version: String by rootProject
val fabric_api_version: String by rootProject
val architectury_api_version: String by rootProject

val techreborn_energy_version: String by rootProject

dependencies {
    modImplementation("net.fabricmc:fabric-loader:$fabric_loader_version")

    // Fabric API
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabric_api_version")

    // Architectury API
    modImplementation("dev.architectury:architectury-fabric:$architectury_api_version")

    commonBundle(project(":common", "namedElements")) { isTransitive = false }
    shadowBundle(project(":common", "transformProductionFabric")) { isTransitive = false }

    // TechReborn Energy
    modApi("teamreborn:energy:$techreborn_energy_version") { isTransitive = false }
    include("teamreborn:energy:$techreborn_energy_version") { isTransitive = false }
}

tasks.processResources {
    val props = mapOf(
        "id" to mod_id,
        "version" to project.version,
        "minecraft_version" to minecraft_version,
        "fabric_loader_version" to fabric_loader_version,
        "architectury_api_version" to architectury_api_version,
        "techreborn_energy_version" to techreborn_energy_version
    )

    inputs.properties(props)

    filesMatching("fabric.mod.json") {
        expand(props)
    }
}

tasks.shadowJar {
    configurations = listOf(shadowBundle)
    archiveClassifier = "dev-shadow"
}

tasks.remapJar {
    inputFile = tasks.shadowJar.get().archiveFile
}
