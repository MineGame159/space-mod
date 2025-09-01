@file:Suppress("PropertyName")

architectury {
    common((rootProject.properties["enabled_platforms"] as String).split(","))
}

repositories {
    maven {
        name = "TerraformersMC"
        url = uri("https://maven.terraformersmc.com")
    }
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
}

val fabric_loader_version: String by rootProject
val architectury_api_version: String by rootProject

val jade_version: String by rootProject
val emi_version: String by rootProject

dependencies {
    // We depend on Fabric Loader here to use the Fabric @Environment annotations,
    // which get remapped to the correct annotations on each platform.
    // Do NOT use other classes from Fabric Loader.
    modImplementation("net.fabricmc:fabric-loader:$fabric_loader_version")

    // Architectury API
    modImplementation("dev.architectury:architectury:$architectury_api_version")

    // Jade API
    modCompileOnly("maven.modrinth:jade:$jade_version+fabric")

    // Emi API
    modCompileOnly("dev.emi:emi-xplat-intermediary:$emi_version:api")
}

loom {
    accessWidenerPath = file("src/main/resources/spacemod.accesswidener")
}
