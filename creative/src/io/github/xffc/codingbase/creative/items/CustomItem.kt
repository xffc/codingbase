package io.github.xffc.codingbase.creative.items

import org.bukkit.event.inventory.InventoryClickEvent

sealed class CustomItem {


    abstract fun onClick(event: InventoryClickEvent)
}