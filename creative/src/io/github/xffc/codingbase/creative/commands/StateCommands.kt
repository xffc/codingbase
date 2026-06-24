package io.github.xffc.codingbase.creative.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.github.xffc.codingbase.creative.commands.AbstractCommand.Companion.executes
import io.github.xffc.codingbase.creative.extensions.creative
import io.github.xffc.codingbase.creative.worlds.CreativeWorld
import io.github.xffc.codingbase.creative.worlds.state.*
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player

interface StateCommands {
    val stateGetter: (CreativeWorld) -> WorldState

    fun LiteralArgumentBuilder<CommandSourceStack>.createCommand() {
        requires { it.sender is Player }
        executes(::changeState)
    }

    fun changeState(context: CommandContext<CommandSourceStack>) {
        val player = context.source.sender as Player
        val world = player.world.creative ?: return
        if (!world.info.hasPermissions(player)) return
        world.changeState(stateGetter(world))
    }

    @Suppress("unused")
    object Play : AbstractCommand("play"), StateCommands {
        override val stateGetter: (CreativeWorld) -> WorldState = { BuildState(it) }

        override fun LiteralArgumentBuilder<CommandSourceStack>.init() {
            createCommand()
        }
    }

    @Suppress("unused")
    object Build : AbstractCommand("build"), StateCommands {
        override val stateGetter: (CreativeWorld) -> WorldState = { PlayState(it) }

        override fun LiteralArgumentBuilder<CommandSourceStack>.init() {
            createCommand()
        }
    }
}