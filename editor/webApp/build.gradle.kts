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

    sourceSets.commonMain.dependencies {
        implementation(project(":${parent!!.name}:shared"))

        implementation(editorlibs.compose.ui)
    }
}