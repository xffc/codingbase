plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.paperweight)
    alias(libs.plugins.pluginyml)
}

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraft.get()}-R0.1-SNAPSHOT")
}

paper {
    main = "$group.${project.name}.RuntimePlugin"
    bootstrapper = "$group.${project.name}.Bootstrapper"
    loader = "$group.${project.name}.Loader"
    apiVersion = libs.versions.minecraft.get()
    generateLibrariesJson = true
}

tasks.generatePaperPluginDescription {
    useDefaultCentralProxy()
}

tasks.shadowJar {
    archiveClassifier.set("")
    configurations = listOf(project.configurations.getByName("runtimeClasspath"))

    dependencies {
    }
}

tasks.jar {
    enabled = false
}

tasks.build {
    dependsOn(tasks.shadowJar)
}