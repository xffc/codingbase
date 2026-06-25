package io.github.xffc.codingbase.creative.worlds.state

import io.github.xffc.codingbase.creative.CreativePlugin.DEBUG_CODE
import io.github.xffc.codingbase.creative.CreativePlugin.IS_DEBUG_ENV
import io.github.xffc.codingbase.creative.code.CodeRuntime
import io.github.xffc.codingbase.creative.code.events.PlayerJoinEvent
import io.github.xffc.codingbase.creative.worlds.CreativeWorld
import kotlinx.serialization.json.Json
import org.bukkit.entity.Player
import java.io.File

// todo
class PlayState(
    override val world: CreativeWorld
) : WorldState() {
    val runtime = CodeRuntime(
        this,
        if (IS_DEBUG_ENV) DEBUG_CODE
        else File(world.instance.worldFolder, "code.json").let {
            if (it.createNewFile()) it.writeText("[]")
            Json.decodeFromString(it.readText())
        }
    )

    override fun onPlayerJoin(player: Player) {
        runtime.runEvent(PlayerJoinEvent, player)
    }
}