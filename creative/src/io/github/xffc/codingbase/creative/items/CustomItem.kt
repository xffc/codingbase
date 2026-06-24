package io.github.xffc.codingbase.creative.items

import io.github.xffc.codingbase.creative.extensions.customName
import io.github.xffc.codingbase.creative.extensions.namespaced
import io.github.xffc.codingbase.creative.extensions.noStyle
import io.github.xffc.codingbase.creative.extensions.setTag
import io.github.xffc.codingbase.creative.extensions.translatable
import io.github.xffc.codingbase.creative.menu.AbstractMenu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.UUID

sealed class CustomItem<T>(
    protected val translationPath: String,
    var stack: ItemStack
) {
    val id: UUID = UUID.randomUUID()

    protected abstract val menu: AbstractMenu

    protected abstract val onSetValue: (T) -> Unit

    init {
        activeItems[id] = this
        stack.setTag(customItemKey, PersistentDataType.STRING, id.toString())
    }

    fun destroy() {
        activeItems.remove(id)
    }

    abstract fun getValue(): T
    abstract fun onClick(event: InventoryClickEvent)
    protected abstract fun ItemStack.update(): ItemStack

    companion object {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        val activeItems = mutableMapOf<UUID, CustomItem<*>>()

        val customItemKey = "customitem".namespaced
    }
}