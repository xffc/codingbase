plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(libs.serialization.json)
    implementation(project(":data"))
}

tasks.shadowJar {
    archiveClassifier.set("")
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