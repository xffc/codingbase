package io.github.xffc.codingbase.creative.extensions

import io.github.xffc.codingbase.creative.CreativePlugin
import net.kyori.adventure.sound.Sound
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import java.util.UUID

val String.namespaced: NamespacedKey
    get() = NamespacedKey(CreativePlugin, this)

val String.uuid: UUID?
    get() = runCatching { UUID.fromString(this) }.getOrNull()

fun runSync(consumer: Runnable) =
    Bukkit.getScheduler().runTask(CreativePlugin, consumer)

fun sound(type: Sound.Type, consumer: (Sound.Builder) -> Unit) = Sound.sound()
    .type(type)
    .apply { consumer(this) }
    .build()