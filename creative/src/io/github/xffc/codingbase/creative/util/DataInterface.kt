package io.github.xffc.codingbase.creative.util

import io.github.xffc.codingbase.creative.CreativePlugin.IS_DEBUG_ENV
import io.github.xffc.codingbase.creative.data.CreativeWorldInfo
import io.github.xffc.codingbase.creative.data.Worlds
import io.github.xffc.codingbase.creative.extensions.json
import io.github.xffc.codingbase.creative.worlds.generator.WorldGeneratorType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import net.kyori.adventure.text.Component
import org.jetbrains.exposed.v1.core.DatabaseConfig
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.insertReturning
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import java.util.UUID

object DataInterface {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun connect(url: String, user: String, password: String) {
        val databaseConfig = DatabaseConfig {
            if (IS_DEBUG_ENV) sqlLogger = StdOutSqlLogger
        }

        Database.connect(url, user = user, password = password, databaseConfig = databaseConfig)
    }

    fun getPlayerWorlds(owner: UUID): List<CreativeWorldInfo> =
        Worlds.selectAll()
            .where { Worlds.owner eq owner }
            .map { Worlds.toData(it) }

    fun getPlayerWorld(worldId: UInt): CreativeWorldInfo = Worlds.toData(
        Worlds.selectAll()
            .where { Worlds.id eq worldId }
            .first()
    )

    fun countWorlds(owner: UUID): Long =
        Worlds
            .select(Worlds.id)
            .where { Worlds.owner eq owner }
            .count()

    fun create(info: CreativeWorldInfo) {
        info.id = Worlds.insertAndGetId {
            Worlds.update(info, it)
        }.value
    }
}