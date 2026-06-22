package io.github.xffc.codingbase.creative.util

import io.github.xffc.codingbase.creative.menu.AbstractMenu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

object GlobalListener: Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val menu = event.inventory.holder as? AbstractMenu ?: return
        menu.onClick(event)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val menu = event.inventory.holder as? AbstractMenu ?: return
        menu.onClose(event)
    }
}