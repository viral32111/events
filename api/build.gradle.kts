plugins {
	id( "fabric-loom" )
	kotlin( "jvm" ).version( System.getProperty( "kotlin_version" ) )

	id( "maven-publish" )
}

base {
	archivesName.set( ( project.extra[ "archives_base_name" ] as String ) + "-api" )
}
version = project.extra[ "mod_version" ] as String
group = ( project.extra[ "maven_group" ] as String ) + ".api"

repositories {}

dependencies {

	// Minecraft
	minecraft( "com.mojang", "minecraft", project.extra[ "minecraft_version" ] as String )

	// Minecraft source mappings - https://github.com/FabricMC/yarn
	mappings( "net.fabricmc", "yarn", project.extra[ "yarn_mappings" ] as String, null, "v2" )

	// Fabric Loader - https://github.com/FabricMC/fabric-loader
	modImplementation( "net.fabricmc", "fabric-loader", project.extra[ "loader_version" ] as String )

	// Fabric API - https://github.com/FabricMC/fabric
	modImplementation( "net.fabricmc.fabric-api", "fabric-api", project.extra[ "fabric_version" ] as String )

	// Kotlin support for Fabric - https://github.com/FabricMC/fabric-language-kotlin
	modImplementation( "net.fabricmc", "fabric-language-kotlin", project.extra[ "fabric_language_kotlin_version" ] as String )

}

tasks {
	val javaVersion = JavaVersion.toVersion( ( project.extra[ "java_version" ] as String ).toInt() )

	withType<JavaCompile> {
		options.encoding = "UTF-8"
		sourceCompatibility = javaVersion.toString()
		targetCompatibility = javaVersion.toString()
		options.release.set( javaVersion.toString().toInt() )
	}

	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions {
			jvmTarget = javaVersion.toString()
		}
	}

	jar {
		from( "LICENSE" ) {
			rename { "${ it }_${ base.archivesName.get() }" }
		}
	}

	processResources {

		// Metadata
		filesMatching( "fabric.mod.json" ) {
			expand( mutableMapOf(
				"version" to project.extra[ "mod_version" ] as String,
				"fabricloader" to project.extra[ "loader_version" ] as String,
				"fabric_api" to project.extra[ "fabric_version" ] as String,
				"fabric_language_kotlin" to project.extra[ "fabric_language_kotlin_version" ] as String,
				"minecraft" to project.extra[ "minecraft_version" ] as String,
				"java" to project.extra[ "java_version" ] as String
			) )
		}

		// Mixins
		filesMatching( "*.mixins.json" ) {
			expand( mutableMapOf(
				"java" to project.extra[ "java_version" ] as String
			) )
		}

	}

	java {
		toolchain {
			languageVersion.set( JavaLanguageVersion.of( javaVersion.toString() ) )
		}

		sourceCompatibility = javaVersion
		targetCompatibility = javaVersion

		withSourcesJar()
	}
}

publishing {
	repositories {
		maven {
			name = "GitHubPackages"
			url = uri( "https://maven.pkg.github.com/viral32111/example-mod" )
			credentials {
				username = project.findProperty( "gpr.user" ) as String? ?: System.getenv( "GPR_USERNAME" )
				password = project.findProperty( "gpr.key" ) as String? ?: System.getenv( "GPR_TOKEN" )
			}
		}
	}

	publications {
		register<MavenPublication>( "gpr" ) {
			from( components[ "java" ] )
		}
	}
}
