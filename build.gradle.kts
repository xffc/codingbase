plugins {
    // apply плагинов, которые используются в нескольких местах чтоб загружались только 1 раз
    alias(libs.plugins.kotlin.jvm) apply false
    alias(editorlibs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.shadow) apply false
    alias(libs.plugins.kotlin.serialization) apply false
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

    tasks.withType<Jar> {
        destinationDirectory = file("$rootDir/build")
    }
}