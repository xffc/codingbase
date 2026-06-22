package io.github.xffc.codingbase.creative.extensions

import io.github.xffc.codingbase.creative.data.CreativeWorldInfo.Companion.worldKey
import io.papermc.paper.datacomponent.DataComponentTypes
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun ItemStack.customName(name: Component) = apply {
    setData(DataComponentTypes.CUSTOM_NAME, name)
}

fun <P, C: Any> ItemStack.setTag(key: NamespacedKey, type: PersistentDataType<P, C>, value: C) = apply {
    editPersistentDataContainer {
        it.set(key, type, value)
    }
}

val ItemStack.worldId: UInt?
    get() = persistentDataContainer.get(worldKey, PersistentDataType.LONG)?.toUInt()