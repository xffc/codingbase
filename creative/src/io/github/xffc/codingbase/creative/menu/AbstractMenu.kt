package io.github.xffc.codingbase.creative.menu

import io.github.xffc.codingbase.creative.extensions.customName
import io.github.xffc.codingbase.creative.extensions.translatable
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import kotlin.collections.chunked

abstract class AbstractMenu(
    title: Component = Component.empty(),
    size: Int = 27
) : InventoryHolder {
    protected val inv: Inventory = Bukkit.createInventory(this, size, title)

    abstract fun tick()

    open fun onClick(event: InventoryClickEvent) {}
    open fun onClose(event: InventoryCloseEvent) {}

    override fun getInventory() = inv

    companion object {
        val FILL_ITEM = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)
            .customName(Component.empty())
    }

    abstract class Paged<T>(
        title: Component = Component.empty(),
        private val size: Int = 27
    ) : AbstractMenu(title, size) {
        protected var currentPage = 0
        protected val rows = size / 9
        protected val pageSize = (rows - 1) * 9 // last row will contain arrows

        protected val previousPageItem = ItemStack.of(Material.ARROW)
            .customName("items.paged.previous".translatable())

        protected val nextPageItem = ItemStack.of(Material.ARROW)
            .customName("items.paged.next".translatable())

        protected var pages = reloadPages()

        fun reloadPages(): List<Collection<T>> = getEntries().chunked(pageSize)

        override fun tick() {
            pages[currentPage].forEachIndexed { index, value ->
                inv.setItem(index, itemStack(value))
            }

            val lastRowBounds = (size - 9..<size)
            lastRowBounds.forEach { index ->
                inv.setItem(index, FILL_ITEM)
            }

            inv.setItem(lastRowBounds.first, previousPageItem)
            inv.setItem(lastRowBounds.last, nextPageItem)
        }

        override fun onClick(event: InventoryClickEvent) {
            when (event.currentItem) {
                nextPageItem -> changePage(true)
                previousPageItem -> changePage(false)
            }
        }

        private fun changePage(toNext: Boolean) {
            if (toNext)
                if (currentPage < pages.size - 1) currentPage++
                else return
            else
                if (currentPage > 0) currentPage--
                else return

            tick()
        }

        protected abstract fun itemStack(value: T): ItemStack

        protected abstract fun getEntries(): Collection<T>
    }
}