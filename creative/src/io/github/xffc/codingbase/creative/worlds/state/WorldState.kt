package io.github.xffc.codingbase.creative.worlds.state

import io.github.xffc.codingbase.creative.worlds.CreativeWorld
import org.bukkit.entity.Player

sealed class WorldState {
    abstract val world: CreativeWorld

    open fun onPlayerJoin(player: Player) {}
    open fun onPlayerQuit(player: Player) {}
}