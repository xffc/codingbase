import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(editorlibs.plugins.kotlinJvm)
    alias(editorlibs.plugins.composeMultiplatform)
    alias(editorlibs.plugins.composeCompiler)
}

dependencies {
    implementation(project(":${parent!!.name}:shared"))

    implementation(compose.desktop.currentOs)
    implementation(editorlibs.kotlinx.coroutinesSwing)

    implementation(editorlibs.compose.uiToolingPreview)
}

compose.desktop {
    application {
        mainClass = "${project.group}.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Exe)
            packageName = project.group.toString()
            packageVersion = project.version.toString()
        }
    }
}