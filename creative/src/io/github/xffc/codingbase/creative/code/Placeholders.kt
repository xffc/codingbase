package io.github.xffc.codingbase.creative.code

object Placeholders {
    // todo сделать нормальные плейсхолдеры через лексер+парсер
    fun replace(text: String, context: CodeContext) = text
        .replace("%source%", context.selector.source?.name ?: "")
        .replace("%target%", context.selector.target?.name ?: "")
        .replace("%current%", context.selector.current.joinToString { it.name })
}