import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(editorlibs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets.commonMain {
        kotlin.srcDir("src")

        dependencies {
            implementation(libs.serialization.core)
        }
    }
}