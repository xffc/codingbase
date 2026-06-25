package io.github.xffc.codingbase.creative.code

import org.bukkit.entity.Entity

class TargetSelector(
    val source: Entity? = null,
    val target: Entity? = null
) {
    val current: MutableList<Entity> = mutableListOf()

    init {
        if (source != null) current.add(source)
    }
}