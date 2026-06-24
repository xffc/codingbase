package io.github.xffc.codingbase.creative.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.github.xffc.codingbase.creative.extensions.creative
import io.github.xffc.codingbase.creative.menu.ManageWorldMenu
import io.github.xffc.codingbase.creative.menu.PlayerWorldsMenu
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player

@Suppress("unused")
object ManageWorldCommand: AbstractCommand("manageworld") {
    override fun LiteralArgumentBuilder<CommandSourceStack>.init() {
        requires { it.sender is Player }
        executes(::openMenu)
    }

    private fun openMenu(context: CommandContext<CommandSourceStack>) {
        val player = context.source.sender as Player
        val world = player.world.creative ?: return
        player.openInventory(ManageWorldMenu(player, world).inventory)
    }
}