package io.github.xffc.codingbase.creative.items

import io.github.xffc.codingbase.creative.extensions.customLore
import io.github.xffc.codingbase.creative.extensions.customName
import io.github.xffc.codingbase.creative.extensions.miniMessage
import io.github.xffc.codingbase.creative.extensions.noStyle
import io.github.xffc.codingbase.creative.extensions.setTag
import io.github.xffc.codingbase.creative.extensions.translatable
import io.github.xffc.codingbase.creative.menu.AbstractMenu
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class SwitchItem<T>(
    translationPath: String,
    override val menu: AbstractMenu,
    private val entries: List<Entry<T>>
) : CustomItem<T>(
    translationPath,
    ItemStack.of(Material.STONE).customName("${translationPath}.name".translatable().noStyle)
) {
    var currentIndex = 0

    init {
        stack = stack.update()
    }

    override fun getValue() = entries[currentIndex].value

    override fun ItemStack.update() = withType(entries[currentIndex].material)
        .customLore(entries.mapIndexed { index, entry ->
            val prefixColor = if (index == currentIndex) NamedTextColor.DARK_RED else NamedTextColor.DARK_GRAY

            val entryText = "${translationPath}.${entry.name}".translatable()
                .color(if (index == currentIndex) NamedTextColor.WHITE else NamedTextColor.DARK_GRAY)

            "> ".miniMessage.color(prefixColor).append(entryText).noStyle
        })

    override fun onClick(event: InventoryClickEvent) {
        currentIndex =
            if (event.isLeftClick)
                if (currentIndex >= entries.size - 1) 0
                else currentIndex + 1
            else if (event.isRightClick)
                if (currentIndex <= 0) entries.size - 1
                else currentIndex - 1
            else return

        // todo: play sound
        stack = stack.update()
        menu.tick()
    }

    data class Entry<T>(
        val name: String,
        val value: T,
        val material: Material
    )
}