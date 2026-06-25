package io.github.xffc.codingbase.creative.worlds.state

import io.github.xffc.codingbase.creative.worlds.CreativeWorld
import kotlinx.coroutines.Job
import org.bukkit.GameMode
import org.bukkit.entity.Player

class BuildState(
    override val world: CreativeWorld
) : WorldState() {
    override fun onPlayerJoin(player: Player) {
        val gameMode =
            if (world.info.hasPermissions(player)) GameMode.CREATIVE
            else GameMode.ADVENTURE

        player.gameMode = gameMode
    }
}