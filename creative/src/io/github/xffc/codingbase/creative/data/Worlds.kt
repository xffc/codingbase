package io.github.xffc.codingbase.creative.data

import io.github.xffc.codingbase.creative.extensions.json
import io.github.xffc.codingbase.creative.worlds.generator.WorldGeneratorType
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.UIntIdTable
import org.jetbrains.exposed.v1.core.java.javaUUID
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder

// todo: голоса, иконка мира
object Worlds : UIntIdTable("worlds") {
    val name = text("name")
    val owner = javaUUID("owner")
    val closed = bool("closed")
    val generator = enumeration<WorldGeneratorType>("generator")
    val size = ushort("size")

    fun toData(row: ResultRow) = CreativeWorldInfo(
        row[name].json,
        row[owner],
        row[closed],
        row[generator],
        row[size]
    ).also { it.id = row[id].value }

    fun update(info: CreativeWorldInfo, builder: UpdateBuilder<*>) {
        builder[name] = info.name.json
        builder[owner] = info.owner
        builder[closed] = info.isClosed
        builder[generator] = info.generator
        builder[size] = info.size
    }
}