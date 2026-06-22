package io.github.xffc.codingbase.creative.menu

import io.github.xffc.codingbase.creative.extensions.translatable
import org.bukkit.entity.Player

class CreateWorldMenu(private val player: Player): AbstractMenu("menu.createworld.title".translatable()) {
    val worldTypeItem = SwitchItem()

    override fun tick() {

    }
}