package io.github.xffc.codingbase.creative.commands

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands

sealed class AbstractCommand(name: String) {
    val instance: LiteralCommandNode<CommandSourceStack> = Commands.literal(name).run {
        init()
        build()
    }

    abstract fun LiteralArgumentBuilder<CommandSourceStack>.init()

    companion object {
        val registry = AbstractCommand::class.sealedSubclasses
            .mapNotNull { it.objectInstance }

        fun <T: ArgumentBuilder<CommandSourceStack, T>> T.executes(
            consumer: (CommandContext<CommandSourceStack>) -> Unit
        ): T = executes {
            consumer(it)
            Command.SINGLE_SUCCESS
        }
    }
}