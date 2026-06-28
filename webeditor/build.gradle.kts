plugins {
    alias(editorlibs.plugins.kotlinMultiplatform) apply false
    alias(editorlibs.plugins.composeMultiplatform) apply false
    alias(editorlibs.plugins.composeCompiler) apply false
}

group = "${rootProject.group}.${project.name}"

repositories {
    google()
}

subprojects {
    repositories.addAll(parent!!.repositories)

    group = parent!!.group
}