package io.github.xffc.codingbase.creative.extensions

import io.github.xffc.codingbase.creative.CreativePlugin
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import java.util.UUID

val String.namespaced: NamespacedKey
    get() = NamespacedKey(CreativePlugin, this)

val String.uuid: UUID?
    get() = runCatching { UUID.fromString(this) }.getOrNull()

fun runSync(consumer: Runnable) =
    Bukkit.getScheduler().runTask(CreativePlugin, consumer)