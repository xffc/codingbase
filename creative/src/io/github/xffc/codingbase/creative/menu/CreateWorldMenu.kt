package io.github.xffc.codingbase.creative.menu

import io.github.xffc.codingbase.creative.data.CreativeWorldInfo
import io.github.xffc.codingbase.creative.extensions.customName
import io.github.xffc.codingbase.creative.extensions.plain
import io.github.xffc.codingbase.creative.extensions.runSync
import io.github.xffc.codingbase.creative.extensions.translatable
import io.github.xffc.codingbase.creative.extensions.translate
import io.github.xffc.codingbase.creative.menu.ManageWorldMenu.Companion.generateWorldManageItems
import io.github.xffc.codingbase.creative.menu.PlayerWorldsMenu.Companion.MAX_WORLDS
import io.github.xffc.codingbase.creative.util.DataInterface
import io.github.xffc.codingbase.creative.worlds.CreativeWorldFactory
import io.github.xffc.codingbase.creative.worlds.generator.WorldGeneratorType
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

class CreateWorldMenu(private val player: Player) : AbstractMenu("menu.createworld.title".translatable()) {
    companion object {
        private val cancelCreationItem = ItemStack.of(Material.BARRIER)
            .customName("menu.createworld.cancel".translatable())

        private val createWorldItem = ItemStack.of(Material.LIME_STAINED_GLASS_PANE)
            .customName("menu.createworld.create".translatable())
    }

    val worldInfo = CreativeWorldInfo(
        "items.world.default_name".translate(player.locale(), player.name.plain),
        player.uniqueId,
        true,
        WorldGeneratorType.entries.first(),
        3u // todo
    )

    val items = generateWorldManageItems(this, worldInfo, true)

    init {
        tick()
    }

    override fun tick() {
        items.forEachIndexed { index, item -> inv.setItem(index + 10, item.stack) }
        inv.setItem(15, cancelCreationItem)
        inv.setItem(16, createWorldItem)
    }

    override fun onClick(event: InventoryClickEvent) {
        when (event.currentItem) {
            cancelCreationItem -> cancel()
            createWorldItem -> create()
        }
    }

    private fun create() {
        cancel()

        DataInterface.scope.launch {
            suspendTransaction {
                if (DataInterface.countWorlds(player.uniqueId) >= MAX_WORLDS) return@suspendTransaction null
                DataInterface.create(worldInfo)
            } ?: return@launch

            runSync {
                CreativeWorldFactory.create(worldInfo).join(player)
            }
        }
    }

    private fun cancel() {
        player.closeInventory()
        destroy()
    }

    private fun destroy() {
        items.forEach { it.destroy() }
    }

    override fun onClose(event: InventoryCloseEvent) {
        when (event.reason) {
            InventoryCloseEvent.Reason.DISCONNECT -> destroy()
            InventoryCloseEvent.Reason.PLAYER -> runSync {
                event.player.openInventory(inventory)
            }

            else -> return
        }
    }
}