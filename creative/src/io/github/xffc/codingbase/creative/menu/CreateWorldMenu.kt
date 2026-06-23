package io.github.xffc.codingbase.creative.menu

import io.github.xffc.codingbase.creative.data.CreativeWorldInfo
import io.github.xffc.codingbase.creative.extensions.customName
import io.github.xffc.codingbase.creative.extensions.plain
import io.github.xffc.codingbase.creative.extensions.runSync
import io.github.xffc.codingbase.creative.extensions.translatable
import io.github.xffc.codingbase.creative.extensions.translate
import io.github.xffc.codingbase.creative.items.SwitchItem
import io.github.xffc.codingbase.creative.items.TextInputItem
import io.github.xffc.codingbase.creative.menu.PlayerWorldsMenu.Companion.MAX_WORLDS
import io.github.xffc.codingbase.creative.util.DataInterface
import io.github.xffc.codingbase.creative.worlds.CreativeWorldFactory
import io.github.xffc.codingbase.creative.worlds.generator.WorldGeneratorType
import kotlinx.coroutines.launch
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

    val nameItem = TextInputItem(
        "menu.createworld.worldname",
        this,
        ItemStack.of(Material.NAME_TAG)
            .customName("menu.createworld.worldname.name".translatable()),
        "menu.createworld.worldname.default".translate(player.locale(), player.name.plain)
    )

    val typeItem = SwitchItem(
        "menu.createworld.worldtype",
        this,
        WorldGeneratorType.entries.map {
            SwitchItem.Entry(it.name.lowercase(), it, it.material)
        }
    )

    val closedItem = SwitchItem(
        "menu.createworld.closed",
        this,
        listOf(
            SwitchItem.Entry("closed", true, Material.IRON_DOOR),
            SwitchItem.Entry("open", false, Material.OAK_DOOR)
        )
    )

    init {
        tick()
    }

    override fun tick() {
        inv.setItem(10, nameItem.stack)
        inv.setItem(11, typeItem.stack)
        inv.setItem(12, closedItem.stack)
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
            val info = suspendTransaction {
                if (DataInterface.countWorlds(player.uniqueId) >= MAX_WORLDS) return@suspendTransaction null

                CreativeWorldInfo(
                    nameItem.getValue(),
                    player.uniqueId,
                    closedItem.getValue(),
                    typeItem.getValue(),
                    3u
                ).also { DataInterface.create(it) }
            } ?: return@launch destroy()

            runSync {
                CreativeWorldFactory.create(info).join(player)
            }
        }
    }

    private fun cancel() {
        player.closeInventory()
        destroy()
    }

    private fun destroy() {
        typeItem.destroy()
        nameItem.destroy()
        closedItem.destroy()
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