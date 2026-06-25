package io.github.xffc.codingbase.creative.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.github.xffc.codingbase.creative.menu.PlayerWorldsMenu
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player

@Suppress("unused")
object MyWorldsCommand: AbstractCommand("myworlds") {
    override fun LiteralArgumentBuilder<CommandSourceStack>.init() {
        requires { it.sender is Player }
        executesConsumer(::openMenu)
    }

    private fun openMenu(context: CommandContext<CommandSourceStack>) {
        val player = context.source.sender as Player
        player.openInventory(PlayerWorldsMenu(player).inventory)
    }
}