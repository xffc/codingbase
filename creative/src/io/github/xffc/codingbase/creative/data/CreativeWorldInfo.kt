package io.github.xffc.codingbase.creative.data

import io.github.xffc.codingbase.creative.extensions.customName
import io.github.xffc.codingbase.creative.extensions.namespaced
import io.github.xffc.codingbase.creative.extensions.noStyle
import io.github.xffc.codingbase.creative.extensions.setTag
import io.github.xffc.codingbase.creative.worlds.generator.WorldGeneratorType
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.UUID

data class CreativeWorldInfo(
    val id: UInt,
    var name: Component,
    var owner: UUID,
    val generator: WorldGeneratorType,
    val size: UShort
) {
    // todo: лор мира типа голоса овнер итд
    // еще придумал короче барьер ставить если у игрока нет разрешения на вход
    fun toItemStack() =
        ItemStack.of(Material.GRASS_BLOCK)
            .customName(name.noStyle)
            .setTag(worldKey, PersistentDataType.LONG, id.toLong())

    companion object {
        val worldKey = "world".namespaced
    }
}