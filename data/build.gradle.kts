plugins {
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    compileOnly(libs.serialization.core)
}