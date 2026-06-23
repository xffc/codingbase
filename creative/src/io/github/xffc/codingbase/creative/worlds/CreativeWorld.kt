package io.github.xffc.codingbase.creative.worlds

import io.github.xffc.codingbase.creative.data.CreativeWorldInfo
import io.github.xffc.codingbase.creative.extensions.translatable
import kotlinx.coroutines.Job
import org.bukkit.World
import org.bukkit.entity.Player

// todo: world state
class CreativeWorld(
    val instance: World,
    val info: CreativeWorldInfo
) {
    // todo: ивент захода всё такое, сброс игрока
    fun join(player: Player) {
        if (!info.canJoin(player)) {
            player.sendMessage("messages.closed".translatable())
            return
        }

        player.teleport(instance.spawnLocation)
    }
}