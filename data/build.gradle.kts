plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    compileOnly(libs.serialization.core)
}

sourceSets.main {
    kotlin.srcDir("src")
    java.srcDir("src")
    resources.srcDir("resources")
}