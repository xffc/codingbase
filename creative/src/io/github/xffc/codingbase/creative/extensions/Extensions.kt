package io.github.xffc.codingbase.creative.extensions

import io.github.xffc.codingbase.creative.CreativePlugin
import org.bukkit.NamespacedKey

val String.namespaced: NamespacedKey
    get() = NamespacedKey(CreativePlugin, this)