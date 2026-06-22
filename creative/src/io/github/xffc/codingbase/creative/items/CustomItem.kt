package io.github.xffc.codingbase.creative.items

import org.bukkit.event.inventory.InventoryClickEvent

sealed class CustomItem {
// s

    abstract fun onClick(event: InventoryClickEvent)
}