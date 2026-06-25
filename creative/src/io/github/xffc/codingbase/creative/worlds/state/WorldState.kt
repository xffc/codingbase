package io.github.xffc.codingbase.creative.worlds.state

import io.github.xffc.codingbase.creative.worlds.CreativeWorld
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.bukkit.entity.Player

sealed class WorldState {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    abstract val world: CreativeWorld

    open fun onWorldStart() {}
    open fun onWorldStop() {}

    open fun onPlayerJoin(player: Player) {}
    open fun onPlayerQuit(player: Player) {}
}