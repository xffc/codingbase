package io.github.xffc.codingbase.creative.menu

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

abstract class AbstractMenu(
    title: Component = Component.empty(),
    size: Int = 27
): InventoryHolder {
    protected val inv: Inventory = Bukkit.createInventory(this, size, title)

    abstract fun tick()

    open fun onClick(event: InventoryClickEvent) {}
    open fun onClose(event: InventoryCloseEvent) {}

    override fun getInventory() = inv
}