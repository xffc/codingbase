import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(editorlibs.plugins.kotlinMultiplatform)
    alias(editorlibs.plugins.composeMultiplatform)
    alias(editorlibs.plugins.composeCompiler)
    alias(editorlibs.plugins.kotlinSerialization)
}

kotlin {
    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets.commonMain.dependencies {
        implementation(project(":data"))
        implementation(editorlibs.compose.runtime)
        implementation(editorlibs.compose.foundation)
        implementation(editorlibs.compose.material3)
        implementation(editorlibs.compose.ui)
        implementation(editorlibs.compose.components.resources)
        implementation(editorlibs.compose.uiToolingPreview)
        implementation(editorlibs.androidx.lifecycle.viewmodelCompose)
        implementation(editorlibs.androidx.lifecycle.runtimeCompose)

        implementation(libs.serialization.json)
        implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.2")
        implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
    }
}