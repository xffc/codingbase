package io.github.xffc.codingbase.creative.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.github.xffc.codingbase.creative.commands.AbstractCommand.Companion.executesConsumer
import io.github.xffc.codingbase.creative.extensions.creative
import io.github.xffc.codingbase.creative.worlds.CreativeWorld
import io.github.xffc.codingbase.creative.worlds.state.*
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player

interface StateCommands {
    companion object {
        inline fun <reified S: WorldState> createCommand(
            command: LiteralArgumentBuilder<CommandSourceStack>,
            crossinline stateGetter: (CreativeWorld) -> S
        ): Any = command
            .requires { it.sender is Player }
            .executesConsumer { context ->
                val player = context.source.sender as Player
                val world = player.world.creative ?: return@executesConsumer

                if (!world.info.hasPermissions(player)) return@executesConsumer

                if (world.state is S) world.join(player, true)
                else world.changeState(stateGetter(world))
            }
    }

    @Suppress("unused")
    object Play : AbstractCommand("play"), StateCommands {
        override fun LiteralArgumentBuilder<CommandSourceStack>.init() {
            createCommand(this) { PlayState(it) }
        }
    }

    @Suppress("unused")
    object Build : AbstractCommand("build"), StateCommands {
        override fun LiteralArgumentBuilder<CommandSourceStack>.init() {
            createCommand(this) { BuildState(it) }
        }
    }
}