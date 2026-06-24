package io.github.xffc.codingbase.creative.extensions

import io.github.xffc.codingbase.creative.CreativePlugin
import io.github.xffc.codingbase.creative.data.CreativeWorldInfo.Companion.worldKey
import io.github.xffc.codingbase.creative.worlds.CreativeWorld
import io.github.xffc.codingbase.creative.worlds.CreativeWorldFactory
import net.kyori.adventure.sound.Sound
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.UUID
import kotlin.toUInt

val String.namespaced: NamespacedKey
    get() = NamespacedKey(CreativePlugin, this)

val String.uuid: UUID?
    get() = runCatching { UUID.fromString(this) }.getOrNull()

val ItemStack.worldId: UInt?
    get() = persistentDataContainer
        .get(worldKey, PersistentDataType.LONG)
        ?.toUInt()

val World.creative: CreativeWorld?
    get() = persistentDataContainer.get(worldKey, PersistentDataType.LONG)
        ?.toUInt()
        ?.let { CreativeWorldFactory.activeWorlds[it] }

fun runSync(consumer: Runnable) =
    Bukkit.getScheduler().runTask(CreativePlugin, consumer)

fun sound(type: Sound.Type, consumer: (Sound.Builder) -> Unit) = Sound.sound()
    .type(type)
    .apply { consumer(this) }
    .build()