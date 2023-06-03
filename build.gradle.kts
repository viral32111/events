plugins {
	id( "fabric-loom" )
	id( "maven-publish" )
	kotlin( "jvm" ).version( System.getProperty( "kotlin_version" ) )
}

base {
	archivesName.set( project.extra[ "archives_base_name" ] as String )
}
version = project.extra[ "mod_version" ] as String
group = project.extra[ "maven_group" ] as String

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

	// Testing
	testImplementation( "org.junit.jupiter:junit-jupiter:5.9.3" )
	testImplementation( kotlin( "test" ) )

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
		from( "LICENSE.txt" ) {
			rename { "${ it }_${ base.archivesName.get() }.txt" }
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

	test {
		useJUnitPlatform()
	}
}

// https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#example-using-kotlin-dsl-for-a-single-package-in-the-same-repository
publishing {
	repositories {
		maven {
			name = project.extra[ "maven_repository_name" ] as String
			url = uri( project.extra[ "maven_repository_url" ] as String )

			credentials {
				username = project.findProperty( "gpr.user" ) as String? ?: System.getenv( "USERNAME" )
				password = project.findProperty( "gpr.key" ) as String? ?: System.getenv( "TOKEN" )
			}
		}
	}

	// https://docs.gradle.org/current/userguide/publishing_maven.html
	publications {
		create<MavenPublication>( "gpr" ) {
			groupId = project.extra[ "maven_group" ] as String
			artifactId = project.extra[ "archives_base_name" ] as String
			version = project.extra[ "mod_version" ] as String

			pom {
				name.set( "Events" )
				description.set( "Minecraft mod with callbacks for commonly used mixins." )
				url.set( "https://github.com/viral32111/events" )

				licenses {
					license {
						name.set( "GNU Affero General Public License v3.0" )
						url.set( "https://github.com/viral32111/events/blob/main/LICENSE.txt" )
					}
				}

				developers {
					developer {
						id.set( "viral32111" )
						name.set( "viral32111" )
						url.set( "https://viral32111.com" )
						email.set( "contact@viral32111.com" )
					}
				}

				scm {
					connection.set( "scm:git:git://github.com/viral32111/events.git" )
					developerConnection.set( "scm:git:git://github.com/viral32111/events.git" )
					url.set( "https://github.com/viral32111/events" )
				}
			}

			from( components[ "java" ] )
		}
	}
}
