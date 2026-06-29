import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(editorlibs.plugins.kotlinMultiplatform)
    alias(editorlibs.plugins.composeMultiplatform)
    alias(editorlibs.plugins.composeCompiler)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets.webMain.dependencies {
        implementation(rootProject.project(":data"))

        implementation(project(":webeditor:shared"))

        implementation(editorlibs.compose.ui)
    }
}