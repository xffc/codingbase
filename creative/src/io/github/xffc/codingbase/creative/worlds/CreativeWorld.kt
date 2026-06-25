package io.github.xffc.codingbase.creative.worlds

import io.github.xffc.codingbase.creative.data.CreativeWorldInfo
import io.github.xffc.codingbase.creative.extensions.translatable
import io.github.xffc.codingbase.creative.worlds.state.BuildState
import io.github.xffc.codingbase.creative.worlds.state.WorldState
import org.bukkit.World
import org.bukkit.entity.Player

// todo: world state
class CreativeWorld(
    val instance: World,
    val info: CreativeWorldInfo
) {
    var state: WorldState = BuildState(this)
        private set

    // todo игроков которые уже в мире переспавнить и всё такое
    fun changeState(newState: WorldState) {
        instance.players.forEach { quit(it) }

        state.onWorldStop()
        state = newState
        state.onWorldStart()

        instance.players.forEach { join(it) }
    }

    // todo: сброс игрока
    fun join(player: Player, ignore: Boolean = false) {
        if (!ignore && !info.canJoin(player)) {
            player.sendMessage("messages.closed".translatable())
            return
        }

        state.onPlayerJoin(player)
        player.teleport(instance.spawnLocation)
    }

    fun quit(player: Player) {
        state.onPlayerQuit(player)
    }
}