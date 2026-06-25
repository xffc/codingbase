package io.github.xffc.codingbase.creative.code

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import org.bukkit.entity.Entity

class TargetSelector(
    val source: Entity? = null,
    val target: Entity? = null
): ForwardingAudience {
    val current: MutableList<Entity> = mutableListOf()

    override fun audiences() = current

    init {
        if (source != null) current.add(source)
    }
}