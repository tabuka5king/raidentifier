plugins {
	id("fabric-loom") version "1.6.8"
	id("maven-publish")
	java
}

base {
	archivesName.set("raidentifier")
}

version = rootProject.properties["mod_version"] ?: "1.0.0"
group = rootProject.properties["maven_group"] ?: "dev.tabuka"

repositories {
	mavenCentral()
	maven {
		url = uri("https://maven.fabricmc.net/")
	}
	maven {
		url = uri("https://api.modrinth.com/maven")
	}
	maven {
		url = uri("https://maven.terraformersmc.com/releases/")
	}
}

dependencies {
	val minecraftVersion: String = rootProject.properties["minecraft_version"] as String
	val fabricLoaderVersion: String = rootProject.properties["loader_version"] as String
	val fabricApiVersion: String = rootProject.properties["fabric_version"] as String

	minecraft("com.mojang:minecraft:$minecraftVersion")
	mappings("net.fabricmc:yarn:${minecraftVersion}+build.3:v2")
	modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
	modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
}

tasks.named<ProcessResources>("processResources") {
	inputs.property("version", project.version)

	filesMatching("fabric.mod.json") {
		expand(mapOf(
			"version" to project.version
		))
	}
}

tasks.withType<JavaCompile> {
	options.release.set(21)
}

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21

	withSourcesJar()
}

tasks.jar {
	from("LICENSE") {
		rename { "${it}_${base.archivesName.get()}" }
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}
}
