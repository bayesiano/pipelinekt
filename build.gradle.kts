// TODO import org.jetbrains.dokka.gradle.DokkaTask
import com.code42.version.Version

//val kotlinVersion = "1.3.61"
val kotlinVersion = "1.5.31"

plugins {
    base
//    kotlin("jvm") version "1.3.61"
    kotlin("jvm") version "1.5.31"
    id("idea")
    `maven-publish`
    // TODO id("com.diffplug.gradle.spotless").version("3.26.1")
    // TODO id("org.jetbrains.dokka").version("0.10.0")
    // TODO id("io.gitlab.arturbosch.detekt").version("1.2.2")
    jacoco
}
val githubRepo = System.getenv("GITHUB_REPOSITORY") ?: "code42/pipelinekt"
val groupName = "com.code42.jenkins"
val baseProjectName = "pipelinekt"
val publishedProjects = listOf("core", "internal", "dsl")

allprojects {
    group = "com.code42"
    version = Version.getVersion()

    repositories {
        mavenCentral()
    }

}
/* TODO
val dokka by tasks.getting(DokkaTask::class) {
    outputFormat = "gfm"
    outputDirectory = "${project.rootDir}/docs/dokka"
    configuration {
        sourceLink {
            path = "./"
            url = "https://github.com/$githubRepo/tree/master"
            lineSuffix = "#L"
        }

        subProjects = publishedProjects
    }


}
*/
tasks {
    create("incrementVersion") {
        doLast {
            Version.incrementVersion()
        }
    }
}
/* TODO
tasks.build {
    finalizedBy("dokka")
}
*/
subprojects {
    repositories {
        //if( java.net.InetAddress.)
        maven {
            url = uri("http://ibrhsvn.ibmutua.inet:8081/artifactory/repo")
            isAllowInsecureProtocol = true
        }
    }

    apply(plugin = "org.jetbrains.kotlin.jvm")

    if(!base.archivesBaseName.startsWith("pipelinekt-")) {
        base.archivesBaseName = "pipelinekt-${base.archivesBaseName}"
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8", kotlinVersion))
        implementation(kotlin("reflect", kotlinVersion))
        testImplementation("org.jetbrains.kotlin:kotlin-test")
        testImplementation( "org.jetbrains.kotlin:kotlin-test-junit")
    }

    if(publishedProjects.contains(project.name)) {
        apply(plugin = "org.gradle.maven-publish")
        //apply(plugin = "com.diffplug.gradle.spotless")
        //apply(plugin = "io.gitlab.arturbosch.detekt")
        apply(plugin = "org.gradle.jacoco")
        //apply(plugin = "org.jetbrains.dokka")

        val sourcesJar by tasks.creating(Jar::class) {
            classifier = "sources"
            from(sourceSets.main.get().allSource)
        }

/* TODO
        val dokkaKdoc by tasks.creating(DokkaTask::class) {
            outputFormat = "html"
            outputDirectory = "$buildDir/kdoc"
            configuration {
                sourceLink {
                    path = "./"
                    url = "https://github.com/$githubRepo/tree/master"
                    lineSuffix = "#L"
                }
            }
        }

        val kdocJar by tasks.creating(Jar::class) {
            group = JavaBasePlugin.DOCUMENTATION_GROUP
            dependsOn(dokkaKdoc)
            classifier = "javadoc"
            from("$buildDir/kdoc")
        }

        artifacts {
            add("archives", sourcesJar)
            add("archives", kdocJar)
        }

        tasks.withType<JacocoReport> {
            reports {
                xml.isEnabled = false
                csv.isEnabled = false
                html.isEnabled = true
                html.destination = file("$buildDir/reports/coverage")
            }
        }
*/
        tasks.build {
            finalizedBy("jacocoTestReport")
        }
/* TODO
        spotless {
            kotlin {
                ktlint()
            }
            kotlinGradle {

                target("*.gradle.kts'", "additionalScripts/*.gradle.kts")

                ktlint()
            }
        }

        detekt {
            input = files("src/main/kotlin", "src/test/kotlin")
            baseline = file("detekt-${project.name}-baseline.xml") // Just if you want to create a baseline file.
        }

        tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
           exclude(".*/resources/.*,.* /build/.*")
        }
*/
/* TODO
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    groupId = group.toString()
                    version = project.version.toString()
                    artifactId = base.archivesBaseName
                    from(components["java"])
                    artifact(sourcesJar)
                    artifact(kdocJar)
                }
            }

            repositories {
                mavenLocal()
                maven {
                    name = "GitHubPackages"
                    
                    url = uri("https://maven.pkg.github.com/$githubRepo")
                    val token = System.getenv("GITHUB_TOKEN")
                    if(token != null) {
                        credentials(HttpHeaderCredentials::class) {
                            name = "Authorization"
                            value = "Bearer ${token}"
                        }
                        authentication {
                            create<HttpHeaderAuthentication>("header")
                        }
                    } else {
                        credentials {
                            username = System.getProperty("github.packages.username") ?: System.getenv("GITHUBUSER")
                            password = System.getProperty("github.packages.token") ?: System.getenv("GITHUBTOKEN")
                        }
                    }
                }
            }
        }

 */
    }
}


