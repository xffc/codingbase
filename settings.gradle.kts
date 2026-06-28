rootProject.name = "codingbase"

dependencyResolutionManagement.versionCatalogs.create("editorlibs") {
    from(files("webeditor/libs.versions.toml"))
}

// комментить при пушах чтобы у людей которые клонировали проект он не грузился 10 лет
//enableWebEditor()

include(":creative", ":data", ":compiler")

fun enableWebEditor() =
    include(":webeditor", ":webeditor:webApp", ":webeditor:shared")