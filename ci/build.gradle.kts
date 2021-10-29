val kotlinVersion = "1.3.60"
val version = "0.11.1"

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.60")
    maven
    application
}
repositories {
     maven {
        name = "Github Packages"
        url = uri("https://maven.pkg.github.com/code42/pipelinekt")
        //Use github token if it is available (used in github actions)
        //otherwise fall back to system properties or environment variables
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

application {
    mainClassName = "your.main.package.MainKt"
}

dependencies {
    implementation("com.code42.jenkins:pipelinekt-dsl:$version")
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlintest-junit")
}
