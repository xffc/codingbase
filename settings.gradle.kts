rootProject.name = "codingbase"

dependencyResolutionManagement.versionCatalogs.create("editorlibs") {
    from(files("editor/libs.versions.toml"))
}

// комментить при пушах чтобы у людей которые клонировали проект он не грузился 10 лет
enableEditor("editor")

include(":creative", ":data", ":compiler")

fun enableEditor(prefix: String) =
    include(":$prefix", ":$prefix:webApp", ":$prefix:shared", ":$prefix:desktopApp")