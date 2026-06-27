plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
    alias(libs.plugins.paperweight)
    alias(libs.plugins.pluginyml)
}

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraft.get()}-R0.1-SNAPSHOT")

    paperLibrary(kotlin("stdlib"))
    paperLibrary(kotlin("reflect"))
    paperLibrary(libs.coroutines.core)
    paperLibrary(libs.serialization.json)
    paperLibrary(libs.exposed.core)
    paperLibrary(libs.exposed.json)
    paperLibrary(libs.exposed.jdbc)
    paperLibrary(libs.classgraph)
    paperLibrary("com.h2database:h2:2.4.240")

    implementation(project(":data")) {
        isTransitive = false
    }
}

paper {
    main = "$group.${project.name}.CreativePlugin"
    bootstrapper = "$group.${project.name}.Bootstrapper"
    loader = "$group.${project.name}.Loader"
    apiVersion = libs.versions.minecraft.get()
    generateLibrariesJson = true
}

tasks.generatePaperPluginDescription {
    useDefaultCentralProxy()
}

tasks.shadowJar {
    archiveClassifier.set("")
    configurations = listOf(project.configurations.getByName("runtimeClasspath"))
}

tasks.jar {
    enabled = false
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

sourceSets.main {
    kotlin.srcDir("src")
    java.srcDir("src")
    resources.srcDir("resources")
}

kotlin.jvmToolchain(21)