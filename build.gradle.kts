plugins {
	id( "fabric-loom" )
	kotlin( "jvm" ) version( System.getProperty( "kotlin_version" ) )


	id( "maven-publish" )
	id( "signing" )
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

	// Unit testing
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
				"java" to project.extra[ "java_version" ] as String,
				"minecraft" to project.extra[ "minecraft_version" ] as String,
				"fabricloader" to project.extra[ "loader_version" ] as String,
				"fabric_api" to project.extra[ "fabric_version" ] as String,
				"fabric_language_kotlin" to project.extra[ "fabric_language_kotlin_version" ] as String
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

	/*
	register<Jar>( "sourcesJar" ) {
		archiveClassifier.set( "sources" )
		from( sourceSets[ "main" ].allSource )
	}
	*/

	// Empty javadoc JAR
	register<Jar>( "javadocJar" ) {
		archiveClassifier.set( "javadoc" )

		includeEmptyDirs = false

		from( "." ) {
			include( "README.md" )
		}
	}
}

signing {
	useInMemoryPgpKeys(
		project.findProperty( "gpg.keyId" ) as String? ?: System.getenv( "GPG_KEY_ID" ),
		project.findProperty( "gpg.secretKey" ) as String? ?: System.getenv( "GPG_SECRET_KEY" ),
		project.findProperty( "gpg.password" ) as String? ?: System.getenv( "GPG_PASSWORD" )
	)

	sign( publishing.publications )
}

publishing {
	repositories {
		// https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#example-using-kotlin-dsl-for-a-single-package-in-the-same-repository
		maven {
			name = "GitHubPackages"
			url = uri( "https://maven.pkg.github.com/viral32111/events" )

			credentials {
				username = project.findProperty( "ghpkg.user" ) as String? ?: System.getenv( "GHPKG_USER" )
				password = project.findProperty( "ghpkg.token" ) as String? ?: System.getenv( "GHPKG_TOKEN" )
			}
		}

		// https://central.sonatype.org/publish/publish-gradle/
		maven {
			name = "OSSRH"
			url = uri( "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/" )

			credentials {
				username = project.findProperty( "ossrh.user" ) as String? ?: System.getenv( "OSSRH_USER" )
				password = project.findProperty( "ossrh.token" ) as String? ?: System.getenv( "OSSRH_TOKEN" )
			}
		}
	}

	// https://docs.gradle.org/current/userguide/publishing_maven.html
	publications {
		create<MavenPublication>( "mavenJava" ) {
			groupId = project.extra[ "maven_group" ] as String
			artifactId = project.extra[ "archives_base_name" ] as String
			version = project.extra[ "mod_version" ] as String

			pom {
				name.set( "Events" )
				description.set( "Minecraft mod adding callbacks for commonly used mixins." )
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

			artifact( tasks[ "javadocJar" ] )
			artifact( tasks[ "sourcesJar" ] )
		}
	}
}
