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
    }

    sourceSets.commonMain.dependencies {
        implementation(editorlibs.compose.runtime)
        implementation(editorlibs.compose.foundation)
        implementation(editorlibs.compose.material3)
        implementation(editorlibs.compose.ui)
        implementation(editorlibs.compose.components.resources)
        implementation(editorlibs.compose.uiToolingPreview)
        implementation(editorlibs.androidx.lifecycle.viewmodelCompose)
        implementation(editorlibs.androidx.lifecycle.runtimeCompose)
    }
}