package io.github.xffc.codingbase.creative.worlds

import io.github.xffc.codingbase.creative.data.CreativeWorldInfo
import kotlinx.coroutines.Job
import org.bukkit.World
import org.bukkit.entity.Player

// todo: world state
class CreativeWorld(
    val instance: World,
    val info: CreativeWorldInfo
) {
    // todo: ивент захода всё такое
    fun join(player: Player) {
        player.teleport(instance.spawnLocation)
    }
}