package io.github.xffc.codingbase.creative.data

import io.github.xffc.codingbase.creative.extensions.customLore
import io.github.xffc.codingbase.creative.extensions.customName
import io.github.xffc.codingbase.creative.extensions.namespaced
import io.github.xffc.codingbase.creative.extensions.plain
import io.github.xffc.codingbase.creative.extensions.setTag
import io.github.xffc.codingbase.creative.extensions.translatable
import io.github.xffc.codingbase.creative.worlds.generator.WorldGeneratorType
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.UUID
import kotlin.properties.Delegates

class CreativeWorldInfo(
    var name: Component,
    var owner: UUID,
    var closed: Boolean,
    val generator: WorldGeneratorType,
    val size: UShort
) {
    var id by Delegates.notNull<UInt>()

    // todo: нормальный перм а не isOp
    fun hasPermissions(player: Player) =
        owner == player.uniqueId || player.isOp

    fun canJoin(player: Player) =
        !closed || hasPermissions(player)

    fun toItemStack(forPlayer: Player? = null): ItemStack {
        val lore = mutableListOf(
            Component.empty(),
            "items.world.owner".translatable((Bukkit.getOfflinePlayer(owner).name ?: "???").plain),
            "items.world.id".translatable(id.toString().plain)
        )

        val material =
            if (forPlayer == null || canJoin(forPlayer)) {
                lore.add("items.world.join".translatable())
                Material.GRASS_BLOCK
            } else {
                Material.BARRIER
            }

        return ItemStack.of(material)
            .customName(name)
            .customLore(lore)
            .setTag(worldKey, PersistentDataType.LONG, id.toLong())
    }

    companion object {
        val worldKey = "world".namespaced
    }
}