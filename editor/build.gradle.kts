plugins {
    alias(editorlibs.plugins.kotlinJvm) apply false
    alias(editorlibs.plugins.kotlinMultiplatform) apply false
    alias(editorlibs.plugins.composeMultiplatform) apply false
    alias(editorlibs.plugins.composeCompiler) apply false
}

group = "${rootProject.group}.${project.name}"
version = "1.0.0"

repositories {
    google()
}

subprojects {
    repositories.addAll(parent!!.repositories)

    group = parent!!.group
    version = parent!!.version
}