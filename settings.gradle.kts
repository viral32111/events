pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net") {
			name = "Fabric"
		}

		mavenCentral()
		gradlePluginPortal()
	}

	plugins {
		kotlin("jvm") version (settings.extra["kotlin_version"] as String)

		id("net.fabricmc.fabric-loom") version (settings.extra["loom_version"] as String)
	}
}
