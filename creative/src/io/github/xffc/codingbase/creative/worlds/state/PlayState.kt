package io.github.xffc.codingbase.creative.worlds.state

import io.github.xffc.codingbase.creative.CreativePlugin.DEBUG_CODE
import io.github.xffc.codingbase.creative.CreativePlugin.IS_DEBUG_ENV
import io.github.xffc.codingbase.creative.code.CodeRuntime
import io.github.xffc.codingbase.creative.code.events.PlayerJoinEvent
import io.github.xffc.codingbase.creative.code.events.WorldStartEvent
import io.github.xffc.codingbase.creative.code.events.WorldStopEvent
import io.github.xffc.codingbase.creative.worlds.CreativeWorld
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
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

    override fun onWorldStart() {
        runtime.runEvent(WorldStartEvent)
    }

    // todo: надо сделать чтоб оно было с какой нибудь задержкой между стартом ивента и дестроем ибо происходит race condition ивентов
    override fun onWorldStop() {
        runtime.runEvent(WorldStopEvent)
        runtime.destroy()
    }

    override fun onPlayerJoin(player: Player) {
        runtime.runEvent(PlayerJoinEvent, player)
    }
}