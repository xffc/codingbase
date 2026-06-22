plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "io.github.xffc.${name}"
version = "1.0"

repositories {
    mavenCentral()
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories.addAll(rootProject.repositories)

    apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)

    sourceSets.main {
        kotlin.srcDir("src")
        java.srcDir("src")
        resources.srcDir("resources")
    }
}

kotlin {
    jvmToolchain(25)
}

tasks.jar {
    enabled = false
}