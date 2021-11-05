buildscript {
    repositories {
        maven {
            url = uri("http://ibrhsvn.ibmutua.inet:8081/artifactory/repo")
            isAllowInsecureProtocol = true
        }
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

plugins {
    `kotlin-dsl`
}

repositories {
    maven {
        url = uri("http://ibrhsvn.ibmutua.inet:8081/artifactory/repo")
        isAllowInsecureProtocol = true
    }
    mavenCentral()
    //jcenter()
}