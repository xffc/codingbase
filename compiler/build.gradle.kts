plugins {
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