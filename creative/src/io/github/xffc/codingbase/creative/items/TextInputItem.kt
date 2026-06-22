package io.github.xffc.codingbase.creative.items

import io.github.xffc.codingbase.creative.extensions.customLore
import io.github.xffc.codingbase.creative.extensions.noStyle
import io.github.xffc.codingbase.creative.extensions.runSync
import io.github.xffc.codingbase.creative.extensions.translatable
import io.github.xffc.codingbase.creative.menu.AbstractMenu
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import kotlin.time.Duration.Companion.seconds

class TextInputItem(
    translationPath: String,
    override val menu: AbstractMenu,
    stack: ItemStack,
    var text: Component = Component.empty()
) : CustomItem<Component>(translationPath, stack) {
    private var deferred: CompletableDeferred<Component>? = null

    init {
        this.stack = stack.update()
    }

    override fun getValue() = text

    override fun ItemStack.update() = apply {
        customLore(
            listOf(
                "items.textinput.current"
                    .translatable(text.color(NamedTextColor.WHITE))
                    .noStyle
                    .color(NamedTextColor.DARK_GRAY)
            )
        )
    }

    override fun onClick(event: InventoryClickEvent) {
        deferred = CompletableDeferred()

        event.whoClicked.persistentDataContainer.set(customItemKey, PersistentDataType.STRING, id.toString())

        event.whoClicked.sendMessage("${translationPath}.text".translatable())

        runSync {
            event.whoClicked.closeInventory()
        }

        scope.launch {
            val newValue = withTimeoutOrNull(30.seconds) {
                deferred?.await()
            }

            deferred = null
            if (newValue != null) {
                text = newValue
                stack = stack.update()
                menu.tick()
            }

            runSync {
                event.whoClicked.openInventory(menu.inventory)
            }
        }
    }

    fun complete(message: Component) {
        deferred?.complete(message)
    }
}