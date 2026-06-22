package io.github.xffc.codingbase.creative.data

import io.github.xffc.codingbase.creative.extensions.json
import io.github.xffc.codingbase.creative.worlds.generator.WorldGeneratorType
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.UIntIdTable
import org.jetbrains.exposed.v1.core.java.javaUUID

object Worlds: UIntIdTable("worlds") {
    val name = text("name")
    val owner = javaUUID("owner")
    val generator = enumeration<WorldGeneratorType>("generator")
    val size = ushort("size")

    fun toData(row: ResultRow) = CreativeWorldInfo(
        row[id].value,
        row[name].json,
        row[owner],
        row[generator],
        row[size]
    )
}